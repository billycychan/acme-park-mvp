package com.billycychan.acmepark.permit_service.config;

import com.billycychan.acmepark.permit_service.dto.Permit;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PermitGenerator {
    public static List<Permit> generatePermits() {
        return Arrays.asList(
                new Permit("P_001", "T_001", "M_001",
                        Arrays.asList("ABCD123", "WXYZ789"),
                        LocalDateTime.of(2025, 12, 31, 23, 59)),
                new Permit("P_002", "T_002", "M_002",
                        Arrays.asList("EFGH456"),
                        LocalDateTime.of(2025, 12, 31, 23, 59)),
                new Permit("P_003", "T_003", "M_003",
                        Arrays.asList("IJKL789", "QRST012"),
                        LocalDateTime.of(2025, 12, 31, 23, 59)),
                new Permit("P_004", "T_004", "M_004",
                        Arrays.asList("MNOP321"),
                        LocalDateTime.of(2025, 12, 31, 23, 59)),
                new Permit("P_005", "T_005", "M_005",
                        Arrays.asList("UVWX654", "DEFG890"),
                        LocalDateTime.of(2025, 12, 31, 23, 59)),
                new Permit("P_006", "T_006", "M_006",
                        Arrays.asList("HIJK123"),
                        LocalDateTime.of(2025, 12, 31, 23, 59)),
                new Permit("P_007", "T_007", "M_007",
                        Arrays.asList("LMNO456", "PQRS789"),
                        LocalDateTime.of(2025, 12, 31, 23, 59)),
                new Permit("P_008", "T_008", "M_008",
                        Arrays.asList("TUVW012"),
                        LocalDateTime.of(2025, 12, 31, 23, 59)),
                new Permit("P_009", "T_009", "M_009",
                        Arrays.asList("XYZA345", "BCDE678"),
                        LocalDateTime.of(2025, 12, 31, 23, 59)),
                new Permit("P_010", "T_010", "M_010",
                        Arrays.asList("FGHI901"),
                        LocalDateTime.of(2025, 12, 31, 23, 59)),
                new Permit("P_011", "T_011", "M_011",
                        Arrays.asList("JKLM234", "NOPQ567"),
                        LocalDateTime.of(2025, 2, 28, 23, 59)),
                new Permit("P_012", "T_012", "M_012",
                        Arrays.asList("RSTU890"),
                        LocalDateTime.of(2025, 2, 28, 23, 59)),
                new Permit("P_013", "T_013", "M_013",
                        Arrays.asList("VWXY123", "ZABC456"),
                        LocalDateTime.of(2025, 2, 28, 23, 59)),
                new Permit("P_014", "T_014", "M_014",
                        Arrays.asList("DEFG789"),
                        LocalDateTime.of(2025, 2, 28, 23, 59)),
                new Permit("P_015", "T_015", "M_015",
                        Arrays.asList("HIJK012", "LMNO345"),
                        LocalDateTime.of(2025, 2, 28, 23, 59)),
                new Permit("P_016", "T_016", "M_016",
                        Arrays.asList("PQRS678"),
                        LocalDateTime.of(2025, 2, 28, 23, 59)),
                new Permit("P_017", "T_017", "M_017",
                        Arrays.asList("TUVW901", "XYZA234"),
                        LocalDateTime.of(2025, 2, 28, 23, 59)),
                new Permit("P_018", "T_018", "M_018",
                        Arrays.asList("BCDE567"),
                        LocalDateTime.of(2025, 2, 28, 23, 59)),
                new Permit("P_019", "T_019", "M_019",
                        Arrays.asList("FGHI890", "JKLM123"),
                        LocalDateTime.of(2025, 2, 28, 23, 59)),
                new Permit("P_020", "T_020", "M_020",
                        Arrays.asList("NOPQ456"),
                        LocalDateTime.of(2025, 2, 28, 23, 59))
        );
    }
}