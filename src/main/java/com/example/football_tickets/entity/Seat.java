package com.example.football_tickets.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "seats",
        uniqueConstraints = @UniqueConstraint(name = "uk_match_row_seat", columnNames = {"match_id","row_label","seat_number"})
)
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @Column(name="row_label", nullable=false, length=10)
    private String rowLabel;

    @Column(name="seat_number", nullable=false)
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=12)
    private SeatStatus status = SeatStatus.FREE;

    public Seat() {}

    public Seat(Match match, String rowLabel, Integer seatNumber) {
        this.match = match;
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
        this.status = SeatStatus.FREE;
    }

    public Long getId() { return id; }
    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }
    public String getRowLabel() { return rowLabel; }
    public void setRowLabel(String rowLabel) { this.rowLabel = rowLabel; }
    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }
    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }
}
