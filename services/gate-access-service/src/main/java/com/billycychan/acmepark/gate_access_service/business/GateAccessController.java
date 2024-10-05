package com.billycychan.acmepark.gate_access_service.business;

import com.billycychan.acmepark.gate_access_service.dto.AccessRequest;
import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gate")
public class GateAccessController {
    private final GateAccessServiceImp gateAccessService;

    public GateAccessController(GateAccessServiceImp gateAccessService) {
        this.gateAccessService = gateAccessService;
    }

    @PostMapping("/access")
    public void checkAccess(@RequestParam String transponderId,
                            @RequestParam String gateId,
                            @RequestParam String licencePlate) {
        AccessRequest request  = new AccessRequest();
        request.setTransponderId(transponderId);
        request.setGateId(gateId);
        request.setLicencePlate(licencePlate);
        gateAccessService.validateAccess(request);
    }
}
