package com.billycychan.acmepark.permit_service.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransponderAccess {
    String transponderId;
    String gate;
    String lot;
    Timestamp timestamp;
}
