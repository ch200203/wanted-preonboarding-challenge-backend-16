package com.wanted.preonboarding.ticket.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TicketSellerTest {

    @Mock
    private PerformanceRepository performanceRepository;

    @InjectMocks
    private TicketSeller ticketSeller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Performance createMockPerformance(String isReservable) {
        return Performance.builder()
            .id(UUID.randomUUID())
            .name("Test Performance")
            .price(10000)
            .round(1)
            .type(1)
            .start_date(new Date(2024,01,24))
            .isReserve(isReservable.equals("enable") ? "enable" : "disable")
            .build();
    }

    @ParameterizedTest
    @CsvSource("enable, disable")
    @DisplayName("공연 및 전시 정보 상태에 따른 목록 조회 후 반환합니다.")
    void AllPerformanceInfoList(String IsReserve) {
        Performance mockPerformance = createMockPerformance(IsReserve);

        when(performanceRepository.findByIsReserve(IsReserve))
            .thenReturn(List.of(mockPerformance));
        List<PerformanceInfo> result = ticketSeller.getAllPerformanceInfoList(IsReserve);

        assertThat(result).hasSize(1);
    }
}
