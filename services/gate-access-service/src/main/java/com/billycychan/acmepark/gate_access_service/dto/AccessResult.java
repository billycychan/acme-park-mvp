package com.billycychan.acmepark.gate_access_service.dto;

import lombok.Data;

@Data
public class AccessResult {
    private boolean granted;
    private String message;

    public AccessResult(boolean granted, String message) {
        this.granted = granted;
        this.message = message;
    }
}