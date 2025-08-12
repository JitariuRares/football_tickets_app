package com.example.football_tickets.repository;

import com.example.football_tickets.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByMatchId(Long matchId);
    Optional<Seat> findByMatchIdAndRowLabelAndSeatNumber(Long matchId, String rowLabel, Integer seatNumber);
}
