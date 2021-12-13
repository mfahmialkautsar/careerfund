package id.careerfund.api.services;

import id.careerfund.api.domains.entities.PaymentType;

import java.util.List;

public interface PaymentTypeService {
    List<PaymentType> getPaymentTypes();
}
