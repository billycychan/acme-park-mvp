package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.inbound.RequestReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/gate")
@RestController
public class GateAccessRESTController {

    private final RequestReceiver requestReceiver;

    public GateAccessRESTController(RequestReceiver requestReceiver) {
        this.requestReceiver = requestReceiver;
    }

    @PostMapping(path = "/validate")
    public ResponseEntity<?> createTransponderRequest(@RequestBody TransponderAccessRequest request) {
        requestReceiver.receive(request);
        return ResponseEntity.ok().build();
    }
}
