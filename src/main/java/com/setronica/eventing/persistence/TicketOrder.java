package com.setronica.eventing.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "ticket_order")
public class TicketOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "event_schedule_id")
    private Integer eventScheduleId;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    private BigDecimal price;
    @Column
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'BOOKED'")
    private Status status;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id")
    private PaymentRecord paymentRecord;
}
