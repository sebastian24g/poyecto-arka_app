package com.arka_app.arka.infraestructure.adapter.controller;



import com.arka_app.arka.application.dto.input.StockHistoryRequestDto;
import com.arka_app.arka.application.dto.output.StockHistoryResponseDto;
import com.arka_app.arka.application.mapper.StockHistoryMapper;
import com.arka_app.arka.domain.model.StockHistory;
import com.arka_app.arka.domain.service.StockHistoryServicePort;
import com.arka_app.arka.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stock-history")
@RequiredArgsConstructor
public class StockHistoryController {

    private final StockHistoryServicePort historyService;

    @PostMapping
    public ResponseEntity<ApiResponse<StockHistoryResponseDto>> record(@Valid @RequestBody StockHistoryRequestDto dto) {
        StockHistory domain = StockHistoryMapper.toDomain(dto);
        StockHistory saved = historyService.record(domain);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stock history recorded", StockHistoryMapper.toResponse(saved)));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<StockHistoryResponseDto>>> getByProduct(@PathVariable Long productId) {
        var list = historyService.getByProduct(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stock history for product", StockHistoryMapper.toResponseList(list)));
    }

    @GetMapping("/product/{productId}/period")
    public ResponseEntity<ApiResponse<List<StockHistoryResponseDto>>> getByProductPeriod(
            @PathVariable Long productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to) {
        var list = historyService.getByProductAndPeriod(productId, from, to);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stock history for product in period", StockHistoryMapper.toResponseList(list)));
    }

    @GetMapping("/period")
    public ResponseEntity<ApiResponse<List<StockHistoryResponseDto>>> getByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to) {
        var list = historyService.getByPeriod(from, to);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stock history in period", StockHistoryMapper.toResponseList(list)));
    }
}
