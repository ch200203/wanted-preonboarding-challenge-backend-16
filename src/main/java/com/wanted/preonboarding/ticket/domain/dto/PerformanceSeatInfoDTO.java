package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PerformanceSeatInfoDTO {

    private UUID performanceSeatId;
    private int round;
    private int gate;
    private char line;
    private int seat;
    private String isReserve;

    public static PerformanceSeatInfoDTO of(PerformanceSeatInfo entity) {
        return PerformanceSeatInfoDTO.builder()
            .performanceSeatId(entity.getId())
            .round(entity.getRound())
            .gate(entity.getGate())
            .line(entity.getLine())
            .seat(entity.getSeat())
            .isReserve(entity.getIsReserve())
            .build();
    }
}
