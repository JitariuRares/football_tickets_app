package com.example.football_tickets.dto;

public record CreateReservationRequest(
        Long matchId,
        Long seatId,
        String customerName,
        String customerEmail
) {}
