package id.careerfund.api.controllers;

import id.careerfund.api.domains.models.Interests;
import id.careerfund.api.domains.models.MyInterests;
import id.careerfund.api.domains.models.UpdateInterest;
import id.careerfund.api.services.InterestService;
import id.careerfund.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class InterestController extends HandlerController {
    private final UserService userService;
    private final InterestService interestService;

    @GetMapping("/interests")
    public ResponseEntity<Interests> getInterests() {
        return ResponseEntity.ok(interestService.get());
    }

    @GetMapping("/my/interests")
    public ResponseEntity<MyInterests> getMyInterests(Principal principal) {
        return ResponseEntity.ok(userService.getMyInterests(principal));
    }

    @PostMapping("/my/interests")
    public ResponseEntity<?> saveMyInterests(Principal principal, @Valid @RequestBody UpdateInterest updateInterest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/my/interests").toUriString());
        userService.saveInterests(principal, updateInterest);
        return ResponseEntity.created(uri).build();
    }

//    @PutMapping("/my/interests")
//    public ResponseEntity<?> addMyInterests(Principal principal, @Valid @RequestBody UpdateInterest updateInterest) {
//        userService.addInterests(principal, updateInterest);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/my/interests")
//    public ResponseEntity<?> deleteMyInterests(Principal principal, @Valid @RequestBody UpdateInterest updateInterest) {
//        userService.deleteInterests(principal, updateInterest);
//        return ResponseEntity.ok().build();
//    }
}
