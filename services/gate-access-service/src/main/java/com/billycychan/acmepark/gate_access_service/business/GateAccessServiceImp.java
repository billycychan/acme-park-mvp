package com.billycychan.acmepark.gate_access_service.business;

import com.billycychan.acmepark.gate_access_service.dto.AccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.AccessLogPort;
import com.billycychan.acmepark.gate_access_service.ports.AccessRequestSender;
import com.billycychan.acmepark.gate_access_service.ports.GateControlPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GateAccessServiceImp implements IGateAccessService {

    private final GateControlPort gateControlPort;
    private final AccessLogPort accessLogPort;
    private final AccessRequestSender accessRequestSender;

    public GateAccessServiceImp(GateControlPort gateControlPort,
                                AccessLogPort accessLogPort,
                                AccessRequestSender accessRequestSender) {
        this.gateControlPort = gateControlPort;
        this.accessLogPort = accessLogPort;
        this.accessRequestSender = accessRequestSender;
    }

    @Override
    public void validateAccess(AccessRequest accessRequest) {
        accessRequestSender.send(accessRequest);
        accessLogPort.write(accessRequest);
    }
}
