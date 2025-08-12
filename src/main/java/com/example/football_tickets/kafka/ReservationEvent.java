package com.example.football_tickets.kafka;

public record ReservationEvent(
        Long reservationId,
        Long matchId,
        Long seatId,
        String customerName,
        String customerEmail
) {}
