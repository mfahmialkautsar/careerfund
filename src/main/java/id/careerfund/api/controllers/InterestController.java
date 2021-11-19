package id.careerfund.api.controllers;

import id.careerfund.api.domains.models.Interests;
import id.careerfund.api.domains.models.MyInterest;
import id.careerfund.api.domains.models.AddInterest;
import id.careerfund.api.services.InterestService;
import id.careerfund.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/interests")
@RequiredArgsConstructor
public class InterestController extends HandlerController {
    private final UserService userService;
    private final InterestService interestService;

    @GetMapping("")
    public ResponseEntity<Interests> getInterests() {
        return ResponseEntity.ok(interestService.get());
    }

    @GetMapping("/my")
    public ResponseEntity<MyInterest> getMyInterest(Principal principal) {
        return ResponseEntity.ok(userService.getMyInterest(principal));
    }

    @PostMapping("/my")
    public ResponseEntity<?> saveMyInterest(Principal principal, @Valid @RequestBody AddInterest addInterest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/my").toUriString());
        try {
            userService.saveInterests(principal, addInterest);
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            if (Objects.equals(e.getMessage(), "INTEREST_EXISTS")) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You cannot change your interest", e.getCause());
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }
}
