package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
    @RequiredArgsConstructor
    public class TicketSeller {
        private final PerformanceRepository performanceRepository;
        private final ReservationRepository reservationRepository;
        private long totalAmount = 0L;

        public List<PerformanceInfo> getAllPerformanceInfoList(final String isReserved) {
            return performanceRepository.findByIsReserve(isReserved)
                .stream()
                .map(PerformanceInfo::of)
                .toList();
        }

        public PerformanceInfo getPerformanceInfoDetail(String name) {
            return PerformanceInfo.of(performanceRepository.findByName(name));
        }

        @Transactional
        public boolean reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);

        String enableReserve = info.getIsReserve();

        if (enableReserve.equalsIgnoreCase("enable")) {
            // 1. 결제
            int price = info.getPrice();
            reserveInfo.setAmount(reserveInfo.getAmount() - price);
            // 2. 예매 진행
            reservationRepository.save(Reservation.of(reserveInfo));
            return true;
        }

        return false;
    }

}
