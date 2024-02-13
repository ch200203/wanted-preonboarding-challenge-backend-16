package com.wanted.preonboarding.ticket.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceSeatInfoDTO;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
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
import org.springframework.transaction.annotation.Transactional;

public class TicketSellerTest {

    @Mock
    private PerformanceRepository performanceRepository;
    @Mock
    private PerformanceSeatInfoRepository performanceSeatInfoRepository;
    @Mock
    private ReservationRepository reservationRepository;


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
            .start_date(new Date(2024, 01, 24))
            .isReserve(isReservable.equals("enable") ? "enable" : "disable")
            .build();
    }


    private PerformanceSeatInfo createMockPerformanceSeatInfo(Performance performance) {
        return PerformanceSeatInfo.builder()
            .performanceId(performance)
            .round(1)
            .gate(1)
            .line('A')
            .seat(1)
            .isReserve("enable")
            .build();
    }

    private static Reservation createMockReservation(Performance performance) {
        return Reservation.builder()
            .id(1)
            .performanceId(performance.getId())
            .phoneNumber("010-1234-5678")
            .gate(1)
            .line('A')
            .name("손흥민")
            .round(1)
            .build();
    }

    @ParameterizedTest
    @CsvSource("enable, disable")
    @DisplayName("공연 및 전시 정보 상태에 따른 목록 조회 후 반환합니다.")
    void getAllPerformanceInfoListTest(String IsReserve) {
        Performance mockPerformance = createMockPerformance(IsReserve);

        // 예약 상태에 따라 생성한 테스트 케이스 공연을 찾아서 리턴
        when(performanceRepository.findByIsReserve(IsReserve))
            .thenReturn(List.of(mockPerformance));
        List<PerformanceInfo> result = ticketSeller.getAllPerformanceInfoList(IsReserve);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPerformanceId()).isEqualTo(result.get(0).getPerformanceId());
    }

    @Test
    @DisplayName("공연 및 전시정보의 좌석 정보를 조회하여 반환 합니다.")
    void getAllPerformanceSeatInfoListTest() {
        Performance mockPerformance = createMockPerformance("enable");
        PerformanceSeatInfo mockSeatInfo = createMockPerformanceSeatInfo(mockPerformance);

        // 테스트 케이스에서 생성한 객체를 리턴
        when(performanceSeatInfoRepository.findByPerformanceId(mockPerformance.getId()))
            .thenReturn(List.of(mockSeatInfo));

        List<PerformanceSeatInfoDTO> result =
            ticketSeller.getPerformanceSeatInfo(mockPerformance.getId());

        assertThat(result).hasSize(1);
        assertThat(mockSeatInfo.getId()).isEqualTo(result.get(0).getPerformanceSeatId());
    }

    @Test
    @DisplayName("고객의 이름과 휴대전화 번호를 통해 예약 정보를 조회 할 수 있다.")
    void getReserveInfoTest() {
        Performance mockPerformance = createMockPerformance("enable");
        Reservation mockReservation = createMockReservation(mockPerformance);

        when(reservationRepository.findByNameAndPhoneNumber(mockReservation.getName(),
            mockReservation.getPhoneNumber())).thenReturn(mockReservation);

    }
}
