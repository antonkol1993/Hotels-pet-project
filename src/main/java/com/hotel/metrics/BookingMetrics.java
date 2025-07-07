package com.hotel.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class BookingMetrics {

    private final Counter briefCounter;
    private final Counter detailCounter;

    public BookingMetrics(MeterRegistry registry) {
        this.briefCounter = Counter.builder("hotel_requests_brief_total")
                .description("Количество запросов на КРАТКИЙ список отелей")
                .register(registry);

        this.detailCounter = Counter.builder("hotel_requests_detail_total")
                .description("Количество запросов на ПОДРОБНУЮ ИНФУ об отеле")
                .register(registry);
    }

    public void onBriefRequest() {
        briefCounter.increment();
    }

    public void onDetailRequest() {
        detailCounter.increment();
    }

//    public void onBooking() {
//        Counter.builder("hotel_requests_total")
//                .tag("type", "booking")
//                .description("Количество созданных отелей")
//                .register(registry)
//                .increment();
//    }
}
