package com.billycychan.acmepark.permit_service.config;

import com.billycychan.acmepark.permit_service.dto.Permit;

import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;

public class PermitGenerator {
    private static final Date EXPIRY_DATE = Date.from(LocalDateTime.of(2025, 12, 31, 23, 59)
            .atZone(ZoneId.systemDefault()).toInstant());

    public static List<Permit> generatePermits() {
        return Arrays.asList(
                createPermit("P_001", "T_001", "M_001", "ABCD123", "WXYZ789"),
                createPermit("P_002", "T_002", "M_002", "EFGH456"),
                createPermit("P_003", "T_003", "M_003", "IJKL789", "QRST012"),
                createPermit("P_004", "T_004", "M_004", "MNOP321"),
                createPermit("P_005", "T_005", "M_005", "UVWX654", "DEFG890"),
                createPermit("P_006", "T_006", "M_006", "HIJK123"),
                createPermit("P_007", "T_007", "M_007", "LMNO456", "PQRS789"),
                createPermit("P_008", "T_008", "M_008", "TUVW012"),
                createPermit("P_009", "T_009", "M_009", "XYZA345", "BCDE678"),
                createPermit("P_010", "T_010", "M_010", "FGHI901")
        );
    }

    private static Permit createPermit(String permitId, String transponderId, String memberId, String... licensePlates) {
        return new Permit(permitId, transponderId, memberId, Arrays.asList(licensePlates), EXPIRY_DATE);
    }
}