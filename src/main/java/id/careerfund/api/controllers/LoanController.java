package id.careerfund.api.controllers;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.models.Loan;
import id.careerfund.api.domains.models.LoansResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoanController {
    @GetMapping("/my/loans")
    @Secured({ERole.Constants.LENDER})
    public ResponseEntity<LoansResponse> getLenderLoans(Principal principal) {
        List<Loan> loans = Arrays.asList(
                new Loan("Sergio Marquina", 20000000, 9, 5000000, 4.2),
                new Loan("Raigor", 30000000, 6, 3000000, 6.6),
                new Loan("Huskar", 9000000, 12, 500000, 1.5),
                new Loan("Thomas Shelby", 50000000, 24, 0, 6.9)
        );
        return ResponseEntity.ok(new LoansResponse(loans));
    }
}
