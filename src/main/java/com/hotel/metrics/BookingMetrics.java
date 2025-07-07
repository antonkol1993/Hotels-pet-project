package com.hotel.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class BookingMetrics {

    private final MeterRegistry registry;

    public BookingMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    public void onBriefRequest() {
        Counter.builder("hotel_requests_total")
                .tag("type", "brief")
                .description("Количество запросов на краткий список отелей")
                .register(registry)
                .increment();
    }

    public void onDetailRequest() {
        Counter.builder("hotel_requests_total")
                .tag("type", "detail")
                .description("Количество запросов на подробную информацию об отеле")
                .register(registry)
                .increment();
    }

    public void onBooking() {
        Counter.builder("hotel_requests_total")
                .tag("type", "booking")
                .description("Количество созданных отелей")
                .register(registry)
                .increment();
    }
}
