package com.beeline.beelineapplicationproducer.kafkaDataSet;

import com.beeline.beelineapplicationproducer.BeelineApplicationProducerApplication;
import com.beeline.beelineapplicationproducer.subscribers.CustomSubscriber;
import com.beeline.beelineapplicationproducer.publisher.CustomPublisher;
import com.beeline.beelineapplicationproducer.entities.Order;

import org.apache.kafka.clients.producer.ProducerConfig;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.KafkaSender;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.Map;

/*
отвечает за работу с Kafka
*/
public final class KafkaDataControl extends SerDes {
    private static KafkaDataControl KAFKA_DATA_CONTROL = new KafkaDataControl();

    public static KafkaDataControl getKafkaDataControl() {
        return KAFKA_DATA_CONTROL;
    }

    /*
    сохраняем название топика
    */
    private final String ORDER_STORAGE_TOPIC = BeelineApplicationProducerApplication
            .context
            .getEnvironment()
            .getProperty( "variables.KAFKA_VARIABLES.KAFKA_TOPICS.ORDER_STORAGE_TOPIC" );

    /*
    все настройки и параметры Kafka
    */
    private final Supplier< Map< String, Object > > getKafkaSenderOptions = () -> Map.of(
            ProducerConfig.ACKS_CONFIG, "1",
            ProducerConfig.CLIENT_ID_CONFIG, BeelineApplicationProducerApplication // GROUP ID для Kafka
                    .context
                    .getEnvironment()
                    .getProperty( "variables.KAFKA_VARIABLES.GROUP_ID_FOR_KAFKA" ),
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BeelineApplicationProducerApplication // Хост брокера Kafka
                    .context
                    .getEnvironment()
                    .getProperty( "variables.KAFKA_VARIABLES.KAFKA_BROKER" ),
            // сериализаторы
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class );

    /*
    создаем соединение с брокером через KafkaSender
     */
    private final KafkaSender< String, String > kafkaSender = KafkaSender.create(
            SenderOptions.< String, String >create( this.getKafkaSenderOptions.get() )
                    .maxInFlight( 1024 ) );

    private KafkaDataControl () {
        super.logging( this.getClass().getName() + " was created" );
    }

    /*
    отправляем сообщение в топик
     */
    public final Consumer< Order > writeOrderToKafka = order -> this.kafkaSender
            .createOutbound()
            .send( CustomPublisher.from( this.ORDER_STORAGE_TOPIC, super.serialize( order ) ) )
            .then()
            .doOnError( throwable -> {
                super.logging( throwable );
                this.close();
            } )
            .doOnSuccess( success -> super.logging( "Order from: " + order.getUserId() + " was send at: " + super.newDate() ) )
            .subscribe(
                    new CustomSubscriber<>(
                            topicName -> super.logging( "Kafka got request for topic: " + topicName )
                    )
            );

    /*
    закрываем соединение с Kafka
    */
    private void close () {
        this.kafkaSender.close();
        KAFKA_DATA_CONTROL = null;
        super.logging( this.getClass().getName() + " is closed!!!" );
    }
}