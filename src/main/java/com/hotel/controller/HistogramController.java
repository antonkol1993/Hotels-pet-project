package com.hotel.controller;

import com.hotel.service.HistogramService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/histogram")
@RequiredArgsConstructor
@Tag(name = "Hotel Histogram", description = "API for hotel statistics")
public class HistogramController implements IHistogramController {

    private final HistogramService histogramService;

    @GetMapping("/{param}")
    @Override
    public ResponseEntity<?> getHistogram(
            @PathVariable("param") String param) {
        try {
            Map<String, Long> result = histogramService.getHistogram(param.toLowerCase());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}
