package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.domains.models.ApiResponse;
import id.careerfund.api.services.ClassService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClassController extends HandlerController {
    private final ClassService classService;

    @GetMapping("/classes")
    public ResponseEntity<ApiResponse<List<Class>>> getClasses(
            @RequestParam(required = false) Collection<Long> institution,
            @RequestParam(required = false) Collection<Long> category,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false) Double pmin,
            @RequestParam(required = false) Double pmax,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order
    ) {
        try {
            Page<Class> classes = classService.getClasses(category, institution, search, pmin, pmax, sort, order);
            return ResponseEntity.ok(ApiResponse.<List<Class>>builder()
                    .data(classes.getContent())
                    .page(classes.getPageable().getPageNumber() + 1)
                    .totalElements(classes.getTotalElements())
                    .totalPages(classes.getTotalPages())
                    .build());
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameter cannot be null", e.getCause());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed to get classes. Try again next time", e.getCause());
        }
    }

    @GetMapping("/classes/{classId}")
    public ResponseEntity<ApiResponse<Class>> getClassById(@PathVariable Long classId) {
        try {
            Class aClass = classService.getClassById(classId);
            return ResponseEntity.ok(ApiResponse.<Class>builder()
                    .data(aClass)
                    .build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class ID not found", e.getCause());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed to get class. Try again next time", e.getCause());
        }
    }

    @GetMapping("/my/classes")
    public ResponseEntity<ApiResponse<List<UserClass>>> getMyClasses(Principal principal) {
        return ResponseEntity.ok(ApiResponse.<List<UserClass>>builder().data(classService.getMyClasses(principal)).build());
    }
}
