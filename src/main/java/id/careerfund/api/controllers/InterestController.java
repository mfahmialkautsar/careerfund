package id.careerfund.api.controllers;

import id.careerfund.api.domains.models.Interests;
import id.careerfund.api.domains.models.MyInterests;
import id.careerfund.api.domains.models.UpdateInterest;
import id.careerfund.api.services.InterestService;
import id.careerfund.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

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
    public ResponseEntity<MyInterests> getMyInterests(Principal principal) {
        return ResponseEntity.ok(userService.getMyInterests(principal));
    }

    @PostMapping("/my")
    public ResponseEntity<?> saveMyInterests(Principal principal, @Valid @RequestBody UpdateInterest updateInterest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/my").toUriString());
        userService.saveInterests(principal, updateInterest);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/my")
    public ResponseEntity<?> addMyInterests(Principal principal, @Valid @RequestBody UpdateInterest updateInterest) {
        userService.addInterests(principal, updateInterest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/my")
    public ResponseEntity<?> deleteMyInterests(Principal principal, @Valid @RequestBody UpdateInterest updateInterest) {
        userService.deleteInterests(principal, updateInterest);
        return ResponseEntity.ok().build();
    }
}
