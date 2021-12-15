package id.careerfund.api.controllers;

import id.careerfund.api.domains.models.ApiResponse;
import id.careerfund.api.domains.models.SimpleUser;
import id.careerfund.api.domains.models.reqres.UpdateUser;
import id.careerfund.api.domains.models.responses.FileUrlResponse;
import id.careerfund.api.domains.models.responses.MyProfile;
import id.careerfund.api.services.UserService;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController extends HandlerController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<SimpleUser> getUser(Principal principal) {
        return ResponseEntity.ok(UserMapper.principalToSimpleUser(principal));
    }

    @GetMapping("/profile/edit")
    public ResponseEntity<UpdateUser> getProfileEdit(Principal principal) {
        return ResponseEntity.ok(userService.getProfileUpdate(principal));
    }

    @PutMapping("/profile/edit")
    public ResponseEntity<UpdateUser> updateProfile(Principal principal, @Valid @RequestBody UpdateUser updateUser) {
        return ResponseEntity.ok(userService.updateUser(principal, updateUser));
    }

    @GetMapping("/profile")
    public ResponseEntity<MyProfile> getProfile(Principal principal) {
        return ResponseEntity.ok(userService.getMyProfile(principal));
    }

    @PutMapping("profile/photo")
    public ResponseEntity<ApiResponse<FileUrlResponse>> uploadPhoto(Principal principal, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(ApiResponse.<FileUrlResponse>builder().data(userService.uploadPhoto(principal, file)).build());
    }

//    @PutMapping("profile/identity-card")
//    public ResponseEntity<ApiResponse<FileUrlResponse>> uploadIdentityCard(Principal principal, @RequestParam MultipartFile file) {
//        return ResponseEntity.ok()
//    }
}
