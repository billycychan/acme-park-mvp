package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.ports.GateControlPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
@Slf4j
public class GateControl implements GateControlPort {

    @Override
    public void openGate(String parkingLot, String gate) {
        log.info("I am a GateControl, I am now opening for the lot {} ,gate {}", parkingLot, gate);
    }

    @Override
    public void closeGate(String parkingLot, String gate) {
        log.info("I am a GateControl, I am now closing for the lot {}, gate {}", parkingLot, gate);
    }
}
