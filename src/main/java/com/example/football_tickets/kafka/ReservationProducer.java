package com.example.football_tickets.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReservationProducer {
    private final KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    public ReservationProducer(KafkaTemplate<String, ReservationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendReservationEvent(ReservationEvent event) {
        kafkaTemplate.send("ticket_reservations", event.reservationId().toString(), event);
    }
}
