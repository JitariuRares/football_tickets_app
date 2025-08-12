package com.example.football_tickets.dto;

public record ReservationResponse(
        Long reservationId,
        String status,
        Long matchId,
        Long seatId,
        String message
) {}
