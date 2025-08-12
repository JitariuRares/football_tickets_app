package com.example.football_tickets.repository;

import com.example.football_tickets.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMatchId(Long matchId);
}
