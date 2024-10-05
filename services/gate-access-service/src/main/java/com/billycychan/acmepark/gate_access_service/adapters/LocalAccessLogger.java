package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.ports.AccessLogPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Slf4j
@Service
public class LocalAccessLogger implements AccessLogPort {

    ArrayList<String> logs = new ArrayList<>();

    @Override
    public void write(AccessResult accessResult) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String message = String.format("Access: %s | transponderId %s | Gate: %s | Message: %s",
                LocalDateTime.now().format(formatter),
                accessResult.getTransponderId(),
                accessResult.getGateId(),
                accessResult.getMessage());
        logs.add(message);
        log.info("New Access Added");
        log.info(String.valueOf(logs));
    }
}
