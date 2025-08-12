package com.example.football_tickets.controller;

import com.example.football_tickets.dto.MatchDTO;
import com.example.football_tickets.dto.SeatDTO;
import com.example.football_tickets.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;
    public MatchController(MatchService matchService) { this.matchService = matchService; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatchDTO createMatch(@RequestBody MatchDTO dto,
                                @RequestParam int rows,
                                @RequestParam int seatsPerRow) {
        return matchService.createMatch(dto, rows, seatsPerRow);
    }

    @GetMapping
    public List<MatchDTO> getAll() {
        return matchService.getAllMatches();
    }

    @GetMapping("/{matchId}/seats/free")
    public List<SeatDTO> getFreeSeats(@PathVariable Long matchId) {
        return matchService.getFreeSeats(matchId);
    }
}
