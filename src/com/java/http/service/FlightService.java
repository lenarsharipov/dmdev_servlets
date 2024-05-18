package com.java.http.service;

import com.java.http.dao.FlightDao;
import com.java.http.dto.FlightDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

public final class FlightService {

    private static final FlightService INSTANCE = new FlightService();
    private final FlightDao flightDao = FlightDao.getInstance();

    private FlightService() {
    }

    public static FlightService getInstance() {
        return INSTANCE;
    }

    public List<FlightDto> findAll() {
        return flightDao.findAll().stream()
                .map(flight -> FlightDto.builder()
                        .id(flight.getId())
                        .description(
                            """
                                %s - %s - %s
                            """.formatted(
                                    flight.getDepartureAirportCode(),
                                    flight.getArrivalAirportCode(),
                                    flight.getStatus()))
                        .build())
                .collect(toList());

    }
}
