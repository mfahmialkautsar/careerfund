package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.domains.models.ApiResponse;
import id.careerfund.api.services.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClassController extends HandlerController {
    private final ClassService classService;

    @GetMapping("/classes")
    public ResponseEntity<ApiResponse<List<Class>>> getClasses() {
        return ResponseEntity.ok(ApiResponse.<List<Class>>builder().data(classService.getClasses()).build());
    }

    @GetMapping("/my/classes")
    public ResponseEntity<ApiResponse<List<UserClass>>> getMyClasses(Principal principal) {
        return ResponseEntity.ok(ApiResponse.<List<UserClass>>builder().data(classService.getMyClasses(principal)).build());
    }
}
