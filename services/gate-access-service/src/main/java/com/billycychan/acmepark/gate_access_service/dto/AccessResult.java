package com.billycychan.acmepark.gate_access_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public record AccessResult(@JsonProperty("transponder-id") String transponderId,
                           @JsonProperty("event") PermitValidatedEvent event,
                           @JsonProperty("message") String message) implements Serializable {
}
