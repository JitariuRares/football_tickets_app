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
        var future = kafkaTemplate.send("ticket_reservations", event.reservationId().toString(), event);

        future.whenComplete((res, ex) -> {
            if (ex != null) {
                System.err.printf("Kafka PRODUCE FAILED | key=%s | err=%s%n",
                        event.reservationId(), ex.getMessage());
            } else {
                var md = res.getRecordMetadata();
                System.out.printf("Kafka PRODUCED | topic=%s partition=%d offset=%d key=%s%n",
                        md.topic(), md.partition(), md.offset(), event.reservationId());
            }
        });
    }

}
