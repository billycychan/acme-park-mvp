package com.billycychan.acmepark.gate_access_service.business;

import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.GateAccessUseCases;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/gate")
public class GateAccessController {
    private final GateAccessUseCases gateAccessUseCases;

    @Autowired
    public GateAccessController(GateAccessUseCases gateAccessUseCases) {
        this.gateAccessUseCases = gateAccessUseCases;
    }

    @PostMapping("/access")
    public ResponseEntity<String> checkAccess(@RequestParam String transponderId,
                                              @RequestParam String parkingLot,
                                              @RequestParam String gate,
                                              @RequestParam String licencePlate) {
        TransponderAccessRequest request  = new TransponderAccessRequest();
        request.setTransponderId(transponderId);
        request.setParkingLot(parkingLot);
        request.setGate(gate);
        request.setLicencePlate(licencePlate);

        gateAccessUseCases.processTransponder(request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Transponder Access Request Sent");
    }
}
