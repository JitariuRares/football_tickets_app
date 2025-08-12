package com.example.football_tickets.dto;

import java.time.LocalDateTime;

public record MatchDTO(
        Long id,
        String homeTeam,
        String awayTeam,
        String stadium,
        LocalDateTime kickOff
) {}
