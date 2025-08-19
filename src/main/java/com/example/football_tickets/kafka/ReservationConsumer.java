package com.example.football_tickets.kafka;

import com.example.football_tickets.entity.Reservation;
import com.example.football_tickets.entity.ReservationStatus;
import com.example.football_tickets.entity.Seat;
import com.example.football_tickets.entity.SeatStatus;
import com.example.football_tickets.repository.ReservationRepository;
import com.example.football_tickets.repository.SeatRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReservationConsumer {
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    public ReservationConsumer(ReservationRepository reservationRepository, SeatRepository seatRepository) {
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
    }

    @KafkaListener(
            topics = "ticket_reservations",
            groupId = "football-ticketing",
            containerFactory = "reservationEventKafkaListenerContainerFactory"
    )
    @Transactional
    public void onReservation(ConsumerRecord<String, ReservationEvent> record,
                              org.springframework.kafka.support.Acknowledgment ack) {
        try {
            ReservationEvent event = record.value();
            System.out.printf("Kafka received key=%s, offset=%d, partition=%d%n",
                    record.key(), record.offset(), record.partition());

            Reservation r = reservationRepository.findById(event.reservationId())
                    .orElseThrow(() -> new IllegalStateException("Reservation not found"));
            Seat seat = seatRepository.findById(event.seatId())
                    .orElseThrow(() -> new IllegalStateException("Seat not found"));

            r.setStatus(ReservationStatus.CONFIRMED);
            seat.setStatus(SeatStatus.BOOKED);
            reservationRepository.save(r);
            seatRepository.save(seat);

            System.out.println("Reservation confirmed by consumer.");
            ack.acknowledge();
        } catch (Exception ex) {
            System.err.println("Consumer error: " + ex.getMessage());
            throw ex;
        }
    }

}
