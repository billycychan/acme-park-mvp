package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.ports.GateControlPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GateOpener implements GateControlPort {
    @Override
    public void openGate() {
       log.info("I am a GateOpener. I am opening the gate!!! :-)");
    }
}
