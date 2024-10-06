package com.billycychan.acmepark.gate_access_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * {"action":"GATE_OPEN","parking_lot":"A","gate":"NORTH_ENTRY"}
 */

@Data
public class GateRequest {
    /**
     * OPEN_GATE, CLOSE_GATE
     */
    private String action;

    /**
     * A B C D E
     */
    @JsonProperty("parking_lot")
    private String parkingLot;

    /**
     *  NORTH_ENTRY NORTH_EXIT
     *  SOUTH_ENTRY SOUTH_EXIT
     *  EAST_ENTRY EAST_EXIT
     *  WEST_ENTRY WEST_EXIT
     */
    private String gate;
}
