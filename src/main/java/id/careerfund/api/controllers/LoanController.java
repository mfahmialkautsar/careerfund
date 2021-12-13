package id.careerfund.api.controllers;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.entities.Loan;
import id.careerfund.api.domains.models.ApiResponse;
import id.careerfund.api.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class LoanController extends HandlerController {
    private final LoanService loanService;

    @Secured({ERole.Constants.LENDER})
    @GetMapping("/lender/loans")
    public ResponseEntity<ApiResponse<Page<Loan>>> getLenderMyLoans(
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.<Page<Loan>>builder().data(loanService.getLoans(null, null)).build());
    }

    @Secured({ERole.Constants.LENDER})
    @GetMapping("/lender/my/loans")
    public ResponseEntity<ApiResponse<Page<Loan>>> getLenderLoans(
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.<Page<Loan>>builder().data(loanService.getMyLoans(principal, null, null)).build());
    }
}
