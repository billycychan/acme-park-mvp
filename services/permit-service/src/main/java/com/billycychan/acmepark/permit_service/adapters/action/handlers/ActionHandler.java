package com.billycychan.acmepark.permit_service.adapters.action.handlers;

public interface ActionHandler<T> {
    void handle(String action, T payload);
}