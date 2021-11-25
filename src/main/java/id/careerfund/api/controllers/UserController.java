package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.SimpleUser;
import id.careerfund.api.services.UserService;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<SimpleUser> getUser(Principal principal) {
        return ResponseEntity.ok(UserMapper.principalToSimpleUser(principal));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

}
