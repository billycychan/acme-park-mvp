package com.billycychan.acmepark.permit_service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Permit {
    // e.g P_001, P_002, P_003
    private final String permitId;
    // e.g. T_001, T_002, T_003
    private final String transponderId;
    // e.g. M_001, M_002, N_003
    private final String memberId;
    // e.g. ABCD123, AAAA392...
    private final List<String> licensePlates;
    private final Timestamp expiryDate;
}
