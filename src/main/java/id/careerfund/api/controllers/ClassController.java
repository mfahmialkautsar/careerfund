package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.domains.models.ApiResponse;
import id.careerfund.api.services.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClassController extends HandlerController {
    private final ClassService classService;

    @GetMapping("/classes")
    public ResponseEntity<ApiResponse<List<Class>>> getClasses(
            @RequestParam(required = false) Collection<String> institutions,
            @RequestParam(required = false) Collection<String> categories,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false) Double feeMin,
            @RequestParam(required = false) Double feeMax,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order
    ) {
        Page<Class> classes = classService.getClasses(categories, institutions, search, feeMin, feeMax, sort, order);

        return ResponseEntity.ok(ApiResponse.<List<Class>>builder()
                .data(classes.getContent())
                .page(classes.getPageable().getPageNumber() + 1)
                .totalElements(classes.getTotalElements())
                .totalPages(classes.getTotalPages())
                .build());
    }

    @GetMapping("/my/classes")
    public ResponseEntity<ApiResponse<List<UserClass>>> getMyClasses(Principal principal) {
        return ResponseEntity.ok(ApiResponse.<List<UserClass>>builder().data(classService.getMyClasses(principal)).build());
    }
}
