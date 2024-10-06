package com.billycychan.acmepark.gate_access_service.business;

import com.billycychan.acmepark.gate_access_service.dto.AccessRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/gate")
public class GateAccessController {
    private final IGateAccessService gateAccessService;

    @Autowired
    public GateAccessController(IGateAccessService gateAccessService) {
        this.gateAccessService = gateAccessService;
    }

    @PostMapping("/access")
    public void checkAccess(@RequestParam String transponderId,
                            @RequestParam String parkingLot,
                            @RequestParam String gate,
                            @RequestParam String licencePlate) {
        AccessRequest request  = new AccessRequest();
        request.setTransponderId(transponderId);
        request.setParkingLot(parkingLot);
        request.setGate(gate);
        request.setLicencePlate(licencePlate);
        gateAccessService.validateAccess(request);
    }
}
