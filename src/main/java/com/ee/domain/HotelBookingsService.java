package com.ee.domain;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HotelBookingsService {
    private final String BOOKING_URL_SEGMENT = "/booking";
    @Value("${test.url}")
    private String testUrl;

    public Response getBookings() {
        return RestAssured.get(testUrl + BOOKING_URL_SEGMENT);
    }

    public Response getBookingDetails(int bookingId) {
        return RestAssured.get(testUrl + BOOKING_URL_SEGMENT + "/" + bookingId);
    }


}
