package com.billycychan.acmepark.permit_service.business;

import com.billycychan.acmepark.permit_service.dto.AccessResult;
import com.billycychan.acmepark.permit_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.permit_service.ports.inbound.RequestReceiver;
import com.billycychan.acmepark.permit_service.ports.inbound.RequestValidator;
import com.billycychan.acmepark.permit_service.ports.outbound.ResponseSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Translator implements RequestReceiver {

    private final ResponseSender responseSender;
    private final RequestValidator requestValidator;

    public Translator(ResponseSender responseSender, RequestValidator requestValidator) {
        this.responseSender = responseSender;
        this.requestValidator = requestValidator;
    }

    @Override
    public void receive(TransponderAccessRequest accessRequest) {
        AccessResult result = requestValidator.validateTransponderRequest(accessRequest);
        responseSender.publishAccessResult(result);
    }
}
