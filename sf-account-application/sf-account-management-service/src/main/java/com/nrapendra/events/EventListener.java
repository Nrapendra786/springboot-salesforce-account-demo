package com.nrapendra.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventListener {

    //private InvoiceService invoiceService;

    public EventListener() {
        super();
       // this.invoiceService = invoiceService;
    }

    @KafkaListener(topics = "events")
    public void order( Event event,Acknowledgment acknowledgment) {
        log.info("Received Event " + event.getId());
        //invoiceService.generateInvoice(invoice);
        acknowledgment.acknowledge();
    }
}
