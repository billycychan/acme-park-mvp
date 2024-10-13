package com.billycychan.acmepark.gate_access_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor @Data
public class AccessResult implements Serializable {
    @JsonProperty("transponder-id") String transponderId;
    @JsonProperty("gate") String gate;
    @JsonProperty("lot") String lot;
    @JsonProperty("event") PermitValidatedEvent event;
    @JsonProperty("message") String message ;


    public AccessResult(String transponderId, String gate, String lot, PermitValidatedEvent event, String message) {
        this.transponderId = transponderId;
        this.gate = gate;
        this.lot = lot;
        this.event = event;
        this.message = message;
    }
}