package com.billycychan.acmepark.access_control_service.dto;

import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.sql.Timestamp;
/**
 * {"transponderId":"123","gate":"entry_1","lot":"A"}
 */
@Data
public class TransponderAccess {
    private String transponderId;
    private String gate;
    private String lot;
    private Timestamp timestamp;
}
