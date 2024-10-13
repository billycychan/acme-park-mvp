package com.billycychan.acmepark.access_control_service.business;

import com.billycychan.acmepark.access_control_service.dto.AccessResult;
import com.billycychan.acmepark.access_control_service.dto.PermitStatus;
import com.billycychan.acmepark.access_control_service.dto.TransponderAccess;
import com.billycychan.acmepark.access_control_service.ports.inbound.AccessRequestHandling;
import com.billycychan.acmepark.access_control_service.ports.outbound.GateControl;
import com.billycychan.acmepark.access_control_service.ports.outbound.PermitValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccessControlServiceBusiness implements AccessRequestHandling {

    private final PermitValidation permitValidation;
    private final GateControl gateControl;

    public AccessControlServiceBusiness(PermitValidation permitValidation, GateControl gateControl) {
        this.permitValidation = permitValidation;
        this.gateControl = gateControl;
    }

    @Override
    public void receiveTransponderEntryRequest(TransponderAccess access) {
       permitValidation.send(access);
    }

    @Override
    public void receiveTransponderValidatedResult(AccessResult result) {
        if (result.getStatus().equals(PermitStatus.VALID)) {
            gateControl.openGate(result.getLot(), result.getGate());
        } else {
            gateControl.showError(result.getLot(),result.getGate());
        }
    }
}
