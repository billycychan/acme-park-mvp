package com.billycychan.acmepark.permit_service.adapters;

import com.billycychan.acmepark.permit_service.dto.AccessResult;
import com.billycychan.acmepark.permit_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.permit_service.ports.inbound.RequestReceiver;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AMQPListener {

    @Autowired
    private final RequestReceiver receiver;

    public AMQPListener(RequestReceiver receiver) {
        this.receiver = receiver;
    }

    @RabbitListener(queues = "permit.validated.request.queue")
    public void receiveTransponderAccessRequest(TransponderAccessRequest request) {
        receiver.receive(request);
    }
}
