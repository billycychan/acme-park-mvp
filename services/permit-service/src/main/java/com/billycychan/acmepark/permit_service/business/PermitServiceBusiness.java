package com.billycychan.acmepark.permit_service.business;

import com.billycychan.acmepark.permit_service.dto.TransponderAccess;
import com.billycychan.acmepark.permit_service.ports.inbound.PermitValidateHandling;
import com.billycychan.acmepark.permit_service.ports.outbound.PermitRepository;
import com.billycychan.acmepark.permit_service.ports.outbound.ValidateResponseSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PermitServiceBusiness implements PermitValidateHandling {

    private final PermitRepository permitRepository;
    private final ValidateResponseSender sender;

    public PermitServiceBusiness(PermitRepository permitRepository, ValidateResponseSender sender) {
        this.permitRepository = permitRepository;
        this.sender = sender;
    }

    @Override
    public void receivePermitValidateRequest(TransponderAccess access) {
        var result = permitRepository.validateTransponderRequest(access);
        sender.send(result);
    }
}
