package com.example.football_tickets.controller;

import com.example.football_tickets.dto.CreateReservationRequest;
import com.example.football_tickets.dto.ReservationResponse;
import com.example.football_tickets.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@RequestBody CreateReservationRequest req) {
        return reservationService.createReservation(req);
    }

    @GetMapping("/match/{matchId}")
    public List<ReservationResponse> forMatch(@PathVariable Long matchId) {
        return reservationService.getReservationsForMatch(matchId);
    }
}
