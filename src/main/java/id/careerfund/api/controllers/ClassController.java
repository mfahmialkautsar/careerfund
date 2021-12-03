package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.Bootcamp;
import id.careerfund.api.domains.entities.UserBootcamp;
import id.careerfund.api.domains.models.ApiResponse;
import id.careerfund.api.services.BootcampService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClassController extends HandlerController {
    private final BootcampService bootcampService;

    @GetMapping("/classes")
    public ResponseEntity<ApiResponse<List<Bootcamp>>> getBootcamps() {
        return ResponseEntity.ok(ApiResponse.success(bootcampService.getBootcamps()));
    }

    @GetMapping("/my/classes")
    public ResponseEntity<ApiResponse<List<UserBootcamp>>> getMyClasses(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(bootcampService.getMyBootcamps(principal)));
    }
}
