package com.example.football_tickets.service.impl;

import com.example.football_tickets.dto.CreateReservationRequest;
import com.example.football_tickets.dto.ReservationResponse;
import com.example.football_tickets.entity.Match;
import com.example.football_tickets.entity.Reservation;
import com.example.football_tickets.entity.ReservationStatus;
import com.example.football_tickets.entity.Seat;
import com.example.football_tickets.entity.SeatStatus;
import com.example.football_tickets.kafka.ReservationEvent;
import com.example.football_tickets.kafka.ReservationProducer;
import com.example.football_tickets.repository.MatchRepository;
import com.example.football_tickets.repository.ReservationRepository;
import com.example.football_tickets.repository.SeatRepository;
import com.example.football_tickets.service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {
    private final MatchRepository matchRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationProducer reservationProducer;

    public ReservationServiceImpl(MatchRepository matchRepository,
                                  SeatRepository seatRepository,
                                  ReservationRepository reservationRepository,
                                  ReservationProducer reservationProducer) {
        this.matchRepository = matchRepository;
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.reservationProducer = reservationProducer;
    }

    @Override
    public ReservationResponse createReservation(CreateReservationRequest request) {

        Match match = matchRepository.findById(request.matchId())
                .orElseThrow(() -> new NoSuchElementException("Match not found"));

        Seat seat = seatRepository.findById(request.seatId())
                .orElseThrow(() -> new NoSuchElementException("Seat not found"));

        if (!seat.getMatch().getId().equals(match.getId())) {
            throw new IllegalStateException("Seat does not belong to this match");
        }
        if (seat.getStatus() != SeatStatus.FREE) {
            throw new IllegalStateException("Seat already reserved");
        }

        seat.setStatus(SeatStatus.HELD);
        seatRepository.save(seat);
        Reservation reservation = new Reservation();
        reservation.setMatch(match);
        reservation.setSeat(seat);
        reservation.setCustomerName(request.customerName());
        reservation.setCustomerEmail(request.customerEmail());
        reservation.setStatus(ReservationStatus.PENDING);
        reservationRepository.save(reservation);

        reservationProducer.sendReservationEvent(
                new ReservationEvent(
                        reservation.getId(),
                        match.getId(),
                        seat.getId(),
                        reservation.getCustomerName(),
                        reservation.getCustomerEmail()
                )
        );

        String seatLabel = seat.getRowLabel() + "-" + seat.getSeatNumber();
        return new ReservationResponse(
                reservation.getId(),
                seatLabel,
                match.getId(),
                seat.getId(),
                reservation.getStatus().name()
        );
    }

    @Override
    public List<ReservationResponse> getReservationsForMatch(Long matchId) {
        return reservationRepository.findByMatchId(matchId).stream()
                .map(r -> {
                    String seatLabel = r.getSeat().getRowLabel() + "-" + r.getSeat().getSeatNumber();
                    return new ReservationResponse(
                            r.getId(),
                            seatLabel,
                            r.getMatch().getId(),
                            r.getSeat().getId(),
                            r.getStatus().name()
                    );
                })
                .toList();
    }
}