package id.careerfund.api.controllers;

import id.careerfund.api.domains.models.SimpleUser;
import id.careerfund.api.services.UserService;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/user")
    public ResponseEntity<SimpleUser> getUser(Principal principal) {
        return ResponseEntity.ok(UserMapper.principalToSimpleUser(principal));
    }
}
