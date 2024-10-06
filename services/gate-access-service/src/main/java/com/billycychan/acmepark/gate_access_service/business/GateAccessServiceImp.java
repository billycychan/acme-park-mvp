package com.billycychan.acmepark.gate_access_service.business;

import com.billycychan.acmepark.gate_access_service.dto.AccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.AccessRequestSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GateAccessServiceImp implements IGateAccessService {

    private final AccessRequestSender accessRequestSender;

    public GateAccessServiceImp(AccessRequestSender accessRequestSender) {
        this.accessRequestSender = accessRequestSender;
    }

    @Override
    public void validateAccess(AccessRequest accessRequest) {
        accessRequestSender.send(accessRequest);
    }
}
