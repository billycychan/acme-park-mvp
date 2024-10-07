package com.billycychan.acmepark.gate_access_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TransponderAccessRequest {
    @JsonProperty("transponder-id")
    String transponderId;
    @JsonProperty("gate")
    String gate;
    @JsonProperty("lot")
    String lot;
    @JsonProperty("datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date datetime;
}
