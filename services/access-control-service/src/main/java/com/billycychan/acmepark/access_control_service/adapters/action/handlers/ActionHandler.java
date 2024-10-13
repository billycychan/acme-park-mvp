package com.billycychan.acmepark.access_control_service.adapters.action.handlers;

public interface ActionHandler<T> {
    void handle(String action, T payload);
}