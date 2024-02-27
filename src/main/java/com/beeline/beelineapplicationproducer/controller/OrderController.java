package com.beeline.beelineapplicationproducer.controller;

import com.beeline.beelineapplicationproducer.inspectors.ResponseController;
import com.beeline.beelineapplicationproducer.kafkaDataSet.KafkaDataControl;
import com.beeline.beelineapplicationproducer.entities.Order;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import javax.ws.rs.core.Response;

@CrossOrigin
@RestController
@RequestMapping( value = "/beeline-serv/api/v1/order" )
public final class OrderController extends ResponseController {
    @PostMapping ( value = "/" )
    public Mono< Response > createOrder (
            @RequestBody final Order order
    ) {
        if (
                super.isCollectionNotEmpty( order.getProductList() )
                && super.objectIsNotNull( order.getUserId() )
        ) {
            KafkaDataControl.getKafkaDataControl().writeOrderToKafka.accept( order );
            return Mono.just( super.getResponse( "", javax.ws.rs.core.Response.Status.OK ) );
        }

        return Mono.just( super.getResponse( "", Response.Status.BAD_REQUEST ) );
    }
}
