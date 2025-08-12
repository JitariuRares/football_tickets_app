package com.example.football_tickets.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=80)
    private String homeTeam;

    @Column(nullable=false, length=80)
    private String awayTeam;

    @Column(nullable=false, length=120)
    private String stadium;

    @Column(nullable=false)
    private LocalDateTime kickOff;

    public Match() {}

    public Match(String homeTeam, String awayTeam, String stadium, LocalDateTime kickOff) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.stadium = stadium;
        this.kickOff = kickOff;
    }

    public Long getId() { return id; }
    public String getHomeTeam() { return homeTeam; }
    public void setHomeTeam(String homeTeam) { this.homeTeam = homeTeam; }
    public String getAwayTeam() { return awayTeam; }
    public void setAwayTeam(String awayTeam) { this.awayTeam = awayTeam; }
    public String getStadium() { return stadium; }
    public void setStadium(String stadium) { this.stadium = stadium; }
    public LocalDateTime getKickOff() { return kickOff; }
    public void setKickOff(LocalDateTime kickOff) { this.kickOff = kickOff; }
}
