package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.EmailAvailability;
import id.careerfund.api.domains.models.ErrorResponse;
import id.careerfund.api.domains.models.UserRegister;
import id.careerfund.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/lender")
    public ResponseEntity<?> registerLender(@Valid @RequestBody UserRegister user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/lender").toUriString());
        try {
            userService.registerLender(user);
        } catch (Exception e) {
            if (e.getMessage().equals("EMAIL_UNAVAILABLE")) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Email is taken"));
            }
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/borrower")
    public ResponseEntity<?> registerBorrower(@Valid @RequestBody UserRegister user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/borrower").toUriString());
        try {
            userService.registerBorrower(user);
        } catch (Exception e) {
            if (e.getMessage().equals("EMAIL_UNAVAILABLE")) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Email is taken"));
            }
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/email-availability")
    public ResponseEntity<Boolean> checkEmailAvailability(@Valid @RequestBody EmailAvailability availability) {
        boolean getIsAvailable = userService.getIsEmailAvailable(availability.getEmail());
        return ResponseEntity.ok(getIsAvailable);
    }
}
