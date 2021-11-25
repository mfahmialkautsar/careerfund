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

    @GetMapping("/bootcamp")
    public ResponseEntity<List<Bootcamp>> getAllClass() {
        return ResponseEntity.ok(bootcampService.getBootcamp());
    }
}
