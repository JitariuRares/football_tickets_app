package com.example.football_tickets.service.impl;

import com.example.football_tickets.dto.MatchDTO;
import com.example.football_tickets.dto.SeatDTO;
import com.example.football_tickets.entity.Match;
import com.example.football_tickets.entity.Seat;
import com.example.football_tickets.entity.SeatStatus;
import com.example.football_tickets.repository.MatchRepository;
import com.example.football_tickets.repository.SeatRepository;
import com.example.football_tickets.service.MatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final SeatRepository seatRepository;

    public MatchServiceImpl(MatchRepository matchRepository, SeatRepository seatRepository) {
        this.matchRepository = matchRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public MatchDTO createMatch(MatchDTO dto, int rows, int seatsPerRow) {
        Match match = new Match(
                dto.homeTeam(),
                dto.awayTeam(),
                dto.stadium(),
                dto.kickOff()
        );
        match = matchRepository.save(match);
        for (int r = 0; r < rows; r++) {
            String rowLabel = String.valueOf((char) ('A' + r));
            for (int s = 1; s <= seatsPerRow; s++) {
                Seat seat = new Seat();
                seat.setMatch(match);
                seat.setRowLabel(rowLabel);
                seat.setSeatNumber(s);
                seat.setStatus(SeatStatus.FREE);
                seatRepository.save(seat);
            }
        }

        return new MatchDTO(
                match.getId(),
                match.getHomeTeam(),
                match.getAwayTeam(),
                match.getStadium(),
                match.getKickOff()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchDTO> getAllMatches() {
        return matchRepository.findAll().stream()
                .map(m -> new MatchDTO(m.getId(), m.getHomeTeam(), m.getAwayTeam(), m.getStadium(), m.getKickOff()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatDTO> getFreeSeats(Long matchId) {
        return seatRepository.findByMatchId(matchId).stream()
                .filter(seat -> seat.getStatus() == SeatStatus.FREE)
                .map(s -> new SeatDTO(s.getId(), s.getRowLabel(), s.getSeatNumber(), s.getStatus().name()))
                .toList();
    }
}
