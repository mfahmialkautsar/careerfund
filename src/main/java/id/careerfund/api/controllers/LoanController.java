package id.careerfund.api.controllers;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.entities.Loan;
import id.careerfund.api.domains.models.ApiResponse;
import id.careerfund.api.domains.models.requests.FundLoan;
import id.careerfund.api.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoanController extends HandlerController {
    private final LoanService loanService;

    @Secured({ERole.Constants.LENDER})
    @GetMapping("/lender/loans")
    public ResponseEntity<ApiResponse<List<Loan>>> getLenderMyLoans(
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.<List<Loan>>builder().data(loanService.getLoans(principal, null, null).getContent()).build());
    }

    @Secured({ERole.Constants.LENDER})
    @GetMapping("/lender/my/loans")
    public ResponseEntity<ApiResponse<List<Loan>>> getLenderLoans(
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.<List<Loan>>builder().data(loanService.getMyLoans(principal, null, null).getContent()).build());
    }

    @Secured({ERole.Constants.LENDER})
    @PostMapping("/lender/loans/fund")
    public ResponseEntity<ApiResponse<Loan>> fundLoan(
            Principal principal,
            @Valid @RequestBody FundLoan fundLoan
    ) {
        try {
            return ResponseEntity.ok(ApiResponse.<Loan>builder().data(loanService.fundLoan(principal, fundLoan)).build());
        } catch (RequestRejectedException e) {
            if (e.getMessage().equals("LOAN_FUNDING_FULL"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This loan has been fully funded", e.getCause());
            else if (e.getMessage().equals("MAX_EXCEEDED"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Maximal available fund exceeded.", e.getCause());
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Remaining funding is less than 200.000, available fund is %s", e.getMessage()));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found", e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed to register to a class. Try again next time", e.getCause());
        }
    }
}
