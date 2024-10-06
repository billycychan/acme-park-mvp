package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.AccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.AccessRequestSender;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class GateControlSender implements AccessRequestSender {

    @Value("${app.custom.messaging.outbound-exchange-topic}")
    private String exchange;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public GateControlSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Override
    public void send(AccessRequest accessRequest) {
        Message newMessage = MessageBuilder.withBody(translate(accessRequest).getBytes()).build();
        log.info("AccessRequestSender sends message  {}: {}", exchange, newMessage);
        Message result = rabbitTemplate.sendAndReceive(RabbitConfig.RPC_EXCHANGE, RabbitConfig.RPC_QUEUE1, newMessage);

        String response = "";
        if (result != null) {
            String correlationId = newMessage.getMessageProperties().getCorrelationId();
            log.info("correlationId:{}", correlationId);

            HashMap<String, Object> headers = (HashMap<String, Object>) result.getMessageProperties().getHeaders();

            String msgId = (String) headers.get("spring_returned_message_correlation");

            if (msgId.equals(correlationId)) {
                response = new String(result.getBody());
                log.info("client receive：{}", response);
            }
        }
    }

    private String translate(AccessRequest accessRequest) {
        ObjectMapper mapper= new ObjectMapper();
        try {
            return mapper.writeValueAsString(accessRequest);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
