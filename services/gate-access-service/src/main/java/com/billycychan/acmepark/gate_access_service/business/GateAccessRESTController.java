package com.billycychan.acmepark.gate_access_service.business;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.incoming.GateAccessPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RequestMapping("/gate")
@RestController
public class GateAccessRESTController {

    private final GateAccessPort gateAccessPort;

    public GateAccessRESTController(GateAccessPort gateAccessPort) {
        this.gateAccessPort = gateAccessPort;
    }

    @PostMapping(path = "/validate")
    public ResponseEntity<AccessResult> createTransponderRequest(@RequestBody TransponderAccessRequest request) {
        log.info("Receiving request from POST /gate/validate {}", request);
        AccessResult result = gateAccessPort.validateTransponderAccess(request);
        return ResponseEntity.ok(result);
    }
}
