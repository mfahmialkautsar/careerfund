package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.Bank;
import id.careerfund.api.domains.entities.PaymentType;
import id.careerfund.api.domains.models.responses.ApiResponse;
import id.careerfund.api.services.BankService;
import id.careerfund.api.services.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FinancialController {
    private final PaymentTypeService paymentTypeService;
    private final BankService bankService;

    @GetMapping("/payment-types")
    public ResponseEntity<ApiResponse<List<PaymentType>>> getPaymentTypes() {
        return ResponseEntity
                .ok(ApiResponse.<List<PaymentType>>builder().data(paymentTypeService.getPaymentTypes()).build());
    }

    @GetMapping("/banks")
    public ResponseEntity<ApiResponse<List<Bank>>> getBanks() {
        return ResponseEntity.ok(ApiResponse.<List<Bank>>builder().data(bankService.getBanks()).build());
    }
}
