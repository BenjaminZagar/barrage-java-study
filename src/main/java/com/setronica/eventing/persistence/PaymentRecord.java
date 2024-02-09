package com.setronica.eventing.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "payment_record")
public class PaymentRecord {
    @Id
    private int id;
    @Column(nullable = false)
    private BigDecimal total;
}

