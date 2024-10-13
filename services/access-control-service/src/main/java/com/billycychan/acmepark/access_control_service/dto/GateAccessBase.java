package com.billycychan.acmepark.access_control_service.dto;

import lombok.Data;
import java.sql.Timestamp;
/**
 * {"transponderId":"123","gate":"entry_1","lot":"A","timestamp":"1728839563499"}
 * {"action":"TRANSPONDER_IN","payload":{"transponderId":"T_100","gate":"A","lot":"A","timestamp":"1728839563499"}}
 */

import java.sql.Timestamp;

@Data
public abstract class GateAccessBase {
    protected String gate;
    protected String lot;
    protected Timestamp timestamp;
}
