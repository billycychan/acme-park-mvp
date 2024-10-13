package com.billycychan.acmepark.access_control_service.adapters.action;

public interface Actions {
    public final String REQUEST_TP_VALIDATION = "REQUEST_TP_VALIDATION";
    public final String FINISH_TP_VALIDATION = "FINISH_TP_VALIDATION";
    public final String ENTER_GATE_TRANSPONDER = "ENTER_GATE_TRANSPONDER";

    public final String OPEN_GATE = "OPEN_GATE";
    public final String NOT_OPEN_GATE = "NOT_OPEN_GATE";
}