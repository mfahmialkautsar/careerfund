package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.PaymentType;
import id.careerfund.api.domains.models.responses.ApiResponse;
import id.careerfund.api.services.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentTypeController {
    private final PaymentTypeService paymentTypeService;

    @GetMapping("/payment-types")
    public ResponseEntity<ApiResponse<List<PaymentType>>> getPaymentTypes() {
        return ResponseEntity
                .ok(ApiResponse.<List<PaymentType>>builder().data(paymentTypeService.getPaymentTypes()).build());
    }
}
