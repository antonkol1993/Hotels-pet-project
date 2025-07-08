package com.hotel.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class HotelServiceMetrics {

    private final Counter briefCounter;
    private final Counter detailCounter;
    private final Counter createHotelCounter;
    private final Counter addAmenitiesCounter;
    private final Counter addAmenitiesCallCounter;

    public HotelServiceMetrics(MeterRegistry registry) {
        this.briefCounter = Counter.builder("hotel_requests_brief_total")
                .description("Количество запросов на КРАТКИЙ список отелей")
                .register(registry);

        this.detailCounter = Counter.builder("hotel_requests_detail_total")
                .description("Количество запросов на ПОДРОБНУЮ ИНФУ об отеле")
                .register(registry);
        this.createHotelCounter = Counter.builder("hotel_create_total")
                .description("Количество СОЗДАННЫХ отелей")
                .register(registry);
        this.addAmenitiesCounter = Counter.builder("add_amenities_to_hotel_total")
                .description("Количество ДОБАВЛЕННЫХ УДОБСТВ для отелей")
                .register(registry);
        this.addAmenitiesCallCounter = Counter.builder("add_amenities_call_total")
                .description("Количество ВЫЗОВОВ добавлений удобств")
                .register(registry);

    }

    public void onBriefRequest() {
        briefCounter.increment();
    }

    public void onDetailRequest() {
        detailCounter.increment();
    }
    public void onCreateHotels() {
        createHotelCounter.increment();
    }
    public void onAddAmenitiesCall() {
        addAmenitiesCallCounter.increment();
    }
    public void onAddedAmenitiesCount(Integer count) {
        addAmenitiesCounter.increment(count);
    }

//    public void onBooking() {
//        Counter.builder("hotel_requests_total")
//                .tag("type", "booking")
//                .description("Количество созданных отелей")
//                .register(registry)
//                .increment();
//    }
}
