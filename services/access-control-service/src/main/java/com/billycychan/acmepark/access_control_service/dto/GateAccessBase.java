package com.billycychan.acmepark.access_control_service.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public abstract class GateAccessBase {
    protected String gate;
    protected String lot;
    protected Timestamp timestamp;
}
