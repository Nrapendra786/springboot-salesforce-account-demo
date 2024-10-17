package com.nrapendra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SalesforceCaptureEventListener {


//    private ShipmentService shipmentService;
//
//    public OrderKafkaListener(ShipmentService shipmentService) {
//        super();
//        this.shipmentService = shipmentService;
//    }
//
//    @KafkaListener(topics = "order")
//    public void order(Shipment shipment, Acknowledgment acknowledgment) {
//        log.info("Received shipment " + shipment.getId());
//        shipmentService.ship(shipment);
//        acknowledgment.acknowledge();
//    }
}
