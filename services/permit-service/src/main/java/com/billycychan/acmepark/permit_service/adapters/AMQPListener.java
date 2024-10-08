package com.billycychan.acmepark.permit_service.adapters;

import com.billycychan.acmepark.permit_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.permit_service.ports.inbound.RequestReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AMQPListener {

    @Autowired
    private final RequestReceiver receiver;

    public AMQPListener(RequestReceiver receiver) {
        this.receiver = receiver;
    }

    @RabbitListener(queues = "permit.validated.request.queue", concurrency = "4")
    public void receiveTransponderAccessRequest(TransponderAccessRequest request) {
        receiver.receive(request);
    }
}
