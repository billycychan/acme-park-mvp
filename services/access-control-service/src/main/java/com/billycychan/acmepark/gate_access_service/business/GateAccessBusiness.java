package com.billycychan.acmepark.gate_access_service.business;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.inbound.RequestReceiver;
import com.billycychan.acmepark.gate_access_service.ports.inbound.ResponseReceiver;
import com.billycychan.acmepark.gate_access_service.ports.outbound.GateManagement;
import com.billycychan.acmepark.gate_access_service.ports.outbound.RequestSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GateAccessBusiness implements RequestReceiver, ResponseReceiver {

    private final RequestSender requestSender;
    private final GateManagement gateManager;

    public GateAccessBusiness(RequestSender requestSender, GateManagement gateManager) {
        this.requestSender = requestSender;
        this.gateManager = gateManager;
    }

    @Override
    public void receive(TransponderAccessRequest request) {
        requestSender.send(request);
    }

    @Override
    public void receive(AccessResult result) {
        gateManager.handle(result);
    }
}
