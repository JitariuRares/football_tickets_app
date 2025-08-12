package com.example.football_tickets.repository;

import com.example.football_tickets.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
