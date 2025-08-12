package com.example.football_tickets.service;

import com.example.football_tickets.dto.CreateReservationRequest;
import com.example.football_tickets.dto.ReservationResponse;

import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(CreateReservationRequest request);
    List<ReservationResponse> getReservationsForMatch(Long matchId);
}
