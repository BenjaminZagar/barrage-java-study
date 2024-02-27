package com.setronica.eventing.dto;

import com.setronica.eventing.persistence.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
public class PaymentProviderResponse {
    private int paymentId;
    private PaymentStatus paymentStatus;
}

