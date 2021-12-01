package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.Bootcamp;
import id.careerfund.api.services.BootcampService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BootcampController extends HandlerController{
    private final BootcampService bootcampService;

    @GetMapping("/bootcamps")
    public ResponseEntity<List<Bootcamp>> getBootcamps() {
        return ResponseEntity.ok(bootcampService.getBootcamp());
    }
}
