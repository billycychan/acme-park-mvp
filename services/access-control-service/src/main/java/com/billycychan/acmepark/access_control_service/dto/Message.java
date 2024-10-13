package com.billycychan.acmepark.access_control_service.dto;

import lombok.Data;

/**
 * TRANSPONDER_IN
 * TRANSPONDER_OUT
 * QRCODE_IN
 * QRCODE_OUT
 * {"action":"TRANSPONDER_IN","payload":{"gate:"A","lot":"A"}}
 * @param <T>
 */
@Data
public class Message<T> {
    private String action;
    private T payload;
}