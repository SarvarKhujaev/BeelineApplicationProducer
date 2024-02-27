package com.beeline.beelineapplicationproducer.kafkaDataSet;

import com.beeline.beelineapplication.BeelineApplication;
import com.beeline.beelineapplication.entities.Order;
import com.beeline.beelineapplication.publisher.CustomPublisher;
import com.beeline.beelineapplication.subscribers.CustomSubscriber;
import org.apache.kafka.clients.producer.ProducerConfig;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/*
отвечает за работу с Kafka
*/
public final class KafkaDataControl extends SerDes {
    private static KafkaDataControl KAFKA_DATA_CONTROL = new KafkaDataControl();

    /*
    сохраняем название топика
     */
    private final String ORDER_STORAGE_TOPIC = BeelineApplication
            .context
            .getEnvironment()
            .getProperty( "variables.KAFKA_VARIABLES.KAFKA_TOPICS.ORDER_STORAGE_TOPIC" );

    public static KafkaDataControl getKafkaDataControl() {
        return KAFKA_DATA_CONTROL;
    }

    /*
    все настройки и параметры Kafka
    */
    private final Supplier< Map< String, Object > > getKafkaSenderOptions = () -> Map.of(
            ProducerConfig.ACKS_CONFIG, "1",
            ProducerConfig.CLIENT_ID_CONFIG, BeelineApplication // GROUP ID для Kafka
                    .context
                    .getEnvironment()
                    .getProperty( "variables.KAFKA_VARIABLES.GROUP_ID_FOR_KAFKA" ),
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BeelineApplication // Хост брокера Kafka
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
    private final Consumer< Order > writeActiveTaskToKafka = order -> this.kafkaSender
            .createOutbound()
            .send( CustomPublisher.from( this.ORDER_STORAGE_TOPIC, super.serialize( order ) ) )
            .then()
            .doOnError( super::logging )
            .doOnSuccess( success -> super.logging( "" ) )
            .subscribe(
                    new CustomSubscriber<>(
                            topicName -> super.logging( "Kafka got request for topic: " )
                    )
            );

    /*
    закрываем соединение с Kafka
    */
    public void clear () {
        this.kafkaSender.close();
        KAFKA_DATA_CONTROL = null;
        super.logging( this.getClass().getName() + " is closed successfully" );
    }
}