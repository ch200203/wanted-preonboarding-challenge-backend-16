package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceSeatInfoDTO;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("query")
@RequiredArgsConstructor
public class QueryController {
    private final TicketSeller ticketSeller;

    /**
     * 공연 및 전시 정보 조회(목록)
     * Request : 예매 가능 여부
     * Response : 공연 리스트(정보: 공연명, 회차, 시작 일시, 예매 가능 여부)
     */
    @GetMapping("/all/performance")
    public ResponseEntity<ResponseHandler<List<PerformanceInfo>>> getAllPerformanceInfoList(
        @RequestParam(required = false, name = "isReserved") String isReserved) {
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<List<PerformanceInfo>>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(ticketSeller.getAllPerformanceInfoList(isReserved))
                .build()
            );
    }

    /**
     * 공연 및 전시정보 상세조회(상세 조회)
     */
    @GetMapping("/performance/{id}")
    public ResponseEntity<ResponseHandler<List<PerformanceSeatInfoDTO>>> getPerformanceInfoDetail(
        @PathVariable("id") UUID id) {
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<List<PerformanceSeatInfoDTO>>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(ticketSeller.getPerformanceSeatInfo(id))
                .build()
            );
    }

    /**
     * 예약 조회 시스템
     * Request Message: 고객의 이름, 휴대 전화
     * Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
     */

}
