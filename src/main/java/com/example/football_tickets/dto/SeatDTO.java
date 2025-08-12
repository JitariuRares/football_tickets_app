package com.example.football_tickets.dto;

public record SeatDTO(
        Long id,
        String rowLabel,
        Integer seatNumber,
        String status
) {}
