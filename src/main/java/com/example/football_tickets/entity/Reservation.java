package com.example.football_tickets.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(nullable=false, length=120)
    private String customerName;

    @Column(nullable=false, length=160)
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=12)
    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public Reservation() {}

    public Reservation(Match match, Seat seat, String customerName, String customerEmail) {
        this.match = match;
        this.seat = seat;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.status = ReservationStatus.PENDING;
    }

    public Long getId() { return id; }
    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }
    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
