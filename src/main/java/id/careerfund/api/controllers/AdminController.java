package id.careerfund.api.controllers;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.ApiResponse;
import id.careerfund.api.domains.models.requests.IdRequest;
import id.careerfund.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Secured({ERole.Constants.ADMIN})
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @PutMapping("/verify-user-id")
    public ResponseEntity<ApiResponse> verifyUserIC(@Valid @RequestBody IdRequest idRequest) {
        userService.verifyUserId(idRequest);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @GetMapping("/verifying-users")
    public ResponseEntity<ApiResponse<List<User>>> getVerifyingUser() {
        return ResponseEntity.ok(ApiResponse.<List<User>>builder().data(userService.getWaitingForVerificationUser()).build());
    }
}
