package com.billycychan.acmepark.permit_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor @Data
public class AccessResult implements Serializable {
        @JsonProperty("transponder-id") String transponderId;
        @JsonProperty("event") PermitValidatedEvent event;
        @JsonProperty("message") String message ;
}
