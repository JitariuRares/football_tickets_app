package com.example.football_tickets.service;

import com.example.football_tickets.dto.MatchDTO;
import com.example.football_tickets.dto.SeatDTO;
import java.util.List;

public interface MatchService {
    MatchDTO createMatch(MatchDTO dto, int rows, int seatsPerRow);
    List<MatchDTO> getAllMatches();
    List<SeatDTO> getFreeSeats(Long matchId);
}
