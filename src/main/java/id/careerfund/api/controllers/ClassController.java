package id.careerfund.api.controllers;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.domains.models.requests.PayMyLoan;
import id.careerfund.api.domains.models.requests.UserClassRequest;
import id.careerfund.api.domains.models.responses.ApiResponse;
import id.careerfund.api.services.ClassService;
import id.careerfund.api.services.UserClassService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClassController extends HandlerController {
    private final ClassService classService;
    private final UserClassService userClassService;

    @GetMapping("/classes")
    public ResponseEntity<ApiResponse<List<Class>>> getClasses(
            Principal principal,
            @RequestParam(required = false) Collection<Long> institution,
            @RequestParam(required = false) Collection<Long> category,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false) Double pmin,
            @RequestParam(required = false) Double pmax,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order) {
        try {
            Page<Class> classes = classService.getClasses(principal, category, institution, search, pmin, pmax, sort,
                    order);
            return ResponseEntity.ok(ApiResponse.<List<Class>>builder()
                    .data(classes.getContent())
                    .page(classes.getPageable().getPageNumber() + 1)
                    .totalElements(classes.getTotalElements())
                    .totalPages(classes.getTotalPages())
                    .build());
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameter cannot be null", e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "Failed to get classes. Try again next time", e.getCause());
        }
    }

    @GetMapping("/classes/{classId}")
    public ResponseEntity<ApiResponse<Class>> getClassById(Principal principal, @PathVariable Long classId) {
        try {
            Class aClass = classService.getClassById(principal, classId);
            return ResponseEntity.ok(ApiResponse.<Class>builder()
                    .data(aClass)
                    .build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class ID not found", e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed to get class. Try again next time",
                    e.getCause());
        }
    }

    @Secured({ ERole.Constants.BORROWER })
    @GetMapping("/my/classes")
    public ResponseEntity<ApiResponse<List<UserClass>>> getMyClasses(Principal principal) {
        return ResponseEntity
                .ok(ApiResponse.<List<UserClass>>builder().data(userClassService.getMyClasses(principal)).build());
    }

    @Secured({ ERole.Constants.BORROWER })
    @PostMapping("/my/classes")
    public ResponseEntity<ApiResponse<UserClass>> addMyClass(Principal principal,
            @Valid @RequestBody UserClassRequest userClassRequest) {
        try {
            URI uri = URI
                    .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/my/classes").toUriString());
            UserClass userClass = userClassService.registerClass(principal, userClassRequest);
            return ResponseEntity.created(uri).body(ApiResponse.<UserClass>builder().data(userClass).build());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found", e.getCause());
        } catch (RequestRejectedException e) {
            if (e.getMessage().equals("DOWNPAYMENT_GREATER"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Downpayment greater than expected",
                        e.getCause());
            else if (e.getMessage().equals("DOWNPAYMENT_LESS"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Downpayment less than expected",
                        e.getCause());
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Class registration is over",
                        e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "Failed to register to a class. Try again next time", e.getCause());
        }
    }

    @Secured({ ERole.Constants.BORROWER })
    @GetMapping("/my/classes/{myClassId}")
    public ResponseEntity<ApiResponse<UserClass>> getMyClassById(Principal principal, @PathVariable Long myClassId) {
        try {
            return ResponseEntity.ok(
                    ApiResponse.<UserClass>builder().data(userClassService.getMyClassById(principal, myClassId)).build());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource",
                    e.getCause());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found", e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed to get class. Try again next time",
                    e.getCause());
        }
    }

    @Secured({ ERole.Constants.BORROWER })
    @PostMapping("/my/classes/{myClassId}/pay")
    public ResponseEntity<ApiResponse<UserClass>> payMyClass(Principal principal, @PathVariable Long myClassId,
            @Valid @RequestBody PayMyLoan payMyLoan) {
        try {
            URI uri = URI
                    .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/my/classes/{myClassId}/pay").toUriString());
            return ResponseEntity.created(uri).body(ApiResponse.<UserClass>builder()
                    .data(userClassService.payMyClass(principal, myClassId, payMyLoan)).build());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource",
                    e.getCause());
        } catch (RequestRejectedException e) {
            switch (e.getMessage()) {
                case "LOAN_FINISHED":
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The loan has been finished",
                            e.getCause());
                case "SHOULD_PAY_DOWNPAYMENT":
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please pay the amount of down payment",
                            e.getCause());
                case "SHOULD_PAY_MONTHLYPAYMENT":
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please pay the amount of monthly payment",
                            e.getCause());
                case "WRONG_AMOUNT":
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please pay with the right amount",
                            e.getCause());
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment is over", e.getCause());
            }
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found", e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed pay loan. Please try again later",
                    e.getCause());
        }
    }
}
