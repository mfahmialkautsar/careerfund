package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.models.InterestsResponse;
import id.careerfund.api.domains.models.MyInterestResponse;
import id.careerfund.api.domains.models.UpdateInterest;
import id.careerfund.api.services.InterestService;
import id.careerfund.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/interests")
@RequiredArgsConstructor
public class InterestController extends HandlerController {
    private final UserService userService;
    private final InterestService interestService;

    @GetMapping("")
    public ResponseEntity<InterestsResponse> getInterests() {
        return ResponseEntity.ok(interestService.get());
    }

    @GetMapping("/my")
    public ResponseEntity<MyInterestResponse> getInterestsById(Principal principal) {
        return ResponseEntity.ok(userService.fetchInterests(principal.getName()));
    }

    @PutMapping("")
    public ResponseEntity<?> updateInterests(Principal principal, @Valid @RequestBody UpdateInterest updateInterest) {
        userService.updateInterest(principal.getName(), updateInterest);
        return ResponseEntity.ok().build();
    }
}
