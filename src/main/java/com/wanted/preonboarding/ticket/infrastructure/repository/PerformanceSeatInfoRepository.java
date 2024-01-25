package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, UUID> {
    List<PerformanceSeatInfo> findByPerformanceId(UUID performanceId);
}
