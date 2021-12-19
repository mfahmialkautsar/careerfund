package id.careerfund.api.controllers;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.models.requests.FundLoan;
import id.careerfund.api.domains.models.requests.WithdrawRequest;
import id.careerfund.api.domains.models.responses.ApiResponse;
import id.careerfund.api.domains.models.responses.FundingDto;
import id.careerfund.api.domains.models.responses.LoanDto;
import id.careerfund.api.services.FundingService;
import id.careerfund.api.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoanController extends HandlerController {
    private final LoanService loanService;
    private final FundingService fundingService;

    @Secured({ ERole.Constants.LENDER })
    @GetMapping("/lender/loans")
    public ResponseEntity<ApiResponse<List<LoanDto>>> getLenderMyLoans(
            Principal principal) {
        return ResponseEntity.ok(ApiResponse.<List<LoanDto>>builder()
                .data(loanService.getLoans(principal, null, null).getContent()).build());
    }

    @Secured({ ERole.Constants.LENDER })
    @PostMapping("/lender/loans/{loanId}/fund")
    public ResponseEntity<ApiResponse<FundingDto>> fundLoan(
            Principal principal,
            @PathVariable Long loanId,
            @Valid @RequestBody FundLoan fundLoan) {
        try {
            URI uri = URI
                    .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/lender/loans/fund")
                            .toUriString());
            return ResponseEntity
                    .created(uri)
                    .body(ApiResponse.<FundingDto>builder().data(loanService.fundLoan(principal, loanId, fundLoan)).build());
        } catch (RequestRejectedException e) {
            if (e.getMessage().equals("LOAN_FUNDING_FULL"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This loan has been fully funded",
                        e.getCause());
            else if (e.getMessage().equals("MAX_EXCEEDED"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Maximal available fund exceeded",
                        e.getCause());
            else if (e.getMessage().equals("LOAN_STARTED"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loan has been started",
                        e.getCause());
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Remaining funding is less than 200.000, available fund is %s", e.getMessage()));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found", e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "Failed to fund a loan. Try again next time", e.getCause());
        }
    }

    @Secured({ ERole.Constants.LENDER })
    @GetMapping("/lender/my/loans")
    public ResponseEntity<ApiResponse<List<FundingDto>>> getLenderLoans_ChangedSoon(
            Principal principal) {
        return ResponseEntity.ok(ApiResponse.<List<FundingDto>>builder()
                .data(fundingService.getMyFundings(principal, null, null).getContent()).build());
    }

    @Secured({ ERole.Constants.LENDER })
    @GetMapping("/lender/my/loans/{fundingId}")
    public ResponseEntity<ApiResponse<FundingDto>> getMyLenderLoan(
            Principal principal,
            @PathVariable Long fundingId) {
        try {
            return ResponseEntity.ok(ApiResponse.<FundingDto>builder()
                    .data(fundingService.getMyFundingById(principal, fundingId)).build());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Funding not found", e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "Failed to get a loan. Try again next time", e.getCause());
        }
    }

    @Secured({ ERole.Constants.LENDER })
    @PostMapping("/lender/my/loans/{fundingId}/withdraw")
    public ResponseEntity<ApiResponse> withdrawFunding(Principal principal, @PathVariable Long fundingId,
            @Valid @RequestBody WithdrawRequest withdrawRequest) {
        try {
            fundingService.withdrawLoan(principal, fundingId, withdrawRequest);
            return ResponseEntity.ok(ApiResponse.builder().build());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funding not found",
                    e.getCause());
        } catch (RequestRejectedException e) {
            switch (e.getMessage()) {
                case "LOAN_UNFINISHED":
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loan has not finished yet");
                case "WITHDRAWN":
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Funding already withdrawn");
                default:
                    throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "Failed to withdraw funding. Try again next time", e.getCause());
        }
    }
}
