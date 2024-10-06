package com.billycychan.acmepark.gate_access_service.dto;

import lombok.Data;

@Data
public class GateRequest {
    /**
     * OPEN_GATE, CLOSE_GATE
     */
    private String action;

    /**
     * A B C D E
     */
    private Character parkingLot;

    /**
     *  NORTH_ENTRY NORTH_EXIT
     *  SOUTH_ENTRY SOUTH_EXIT
     *  EAST_ENTRY EAST_EXIT
     *  WEST_ENTRY WEST_EXIT
     */
    private String gate;
}
