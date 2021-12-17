package id.careerfund.api.controllers;

import id.careerfund.api.domains.models.reqres.AssessmentScore;
import id.careerfund.api.domains.models.reqres.UpdateUser;
import id.careerfund.api.domains.models.responses.ApiResponse;
import id.careerfund.api.domains.models.responses.FileUrlResponse;
import id.careerfund.api.domains.models.responses.MyProfile;
import id.careerfund.api.services.UserService;
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

    @PutMapping("/profile/photo")
    public ResponseEntity<ApiResponse<FileUrlResponse>> uploadPhoto(Principal principal,
            @RequestParam MultipartFile file) {
        return ResponseEntity
                .ok(ApiResponse.<FileUrlResponse>builder().data(userService.uploadPhoto(principal, file)).build());
    }

    @PutMapping("/profile/identity-card")
    public ResponseEntity<ApiResponse<FileUrlResponse>> uploadIdentityCard(Principal principal,
            @RequestParam MultipartFile file) {
        return ResponseEntity.ok(
                ApiResponse.<FileUrlResponse>builder().data(userService.uploadIdentityCard(principal, file)).build());
    }

    @PutMapping("/profile/selfie")
    public ResponseEntity<ApiResponse<FileUrlResponse>> uploadSelfie(Principal principal,
            @RequestParam MultipartFile file) {
        return ResponseEntity
                .ok(ApiResponse.<FileUrlResponse>builder().data(userService.uploadSelfie(principal, file)).build());
    }

    @PutMapping("/profile/verify")
    public ResponseEntity<ApiResponse> requestVerifyIdentity(Principal principal) {
        userService.requestVerify(principal);
        return ResponseEntity.ok(ApiResponse.builder().message("Your identity is being verified").build());
    }

    @PutMapping("/profile/assessment-score")
    public ResponseEntity<ApiResponse<AssessmentScore>> saveAssessmentScore(Principal principal,
            @Valid @RequestBody AssessmentScore assessmentScore) {
        return ResponseEntity.ok(ApiResponse.<AssessmentScore>builder()
                .data(userService.saveAssessmentScore(principal, assessmentScore)).build());
    }

    @GetMapping("/profile/assessment-score")
    public ResponseEntity<ApiResponse<AssessmentScore>> getAssessmentScore(Principal principal) {
        return ResponseEntity
                .ok(ApiResponse.<AssessmentScore>builder().data(userService.getAssessmentScore(principal)).build());
    }
}
