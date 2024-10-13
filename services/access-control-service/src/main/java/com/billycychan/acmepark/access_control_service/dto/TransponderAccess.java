package com.billycychan.acmepark.access_control_service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransponderAccess extends GateAccessBase {
    String transponderId;
}
