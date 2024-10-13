package com.billycychan.acmepark.access_control_service.adapters;

import com.billycychan.acmepark.access_control_service.dto.TransponderAccess;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;

@Slf4j
@Component
public class AMQPListener {

    @RabbitListener(queues = "transponder.access.request.queue")
    public void listen(String data) {
        TransponderAccess access = translate(data);
        access.setTimestamp(new Timestamp(System.currentTimeMillis()));
        log.info(String.valueOf(access));
    }

    private TransponderAccess translate(String raw) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(raw, TransponderAccess.class);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
