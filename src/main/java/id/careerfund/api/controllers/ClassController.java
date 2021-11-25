package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.services.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClassController{
    private final ClassService classService;

    @GetMapping("/class")
    public ResponseEntity<List<Class>> getAllClass() {
        return ResponseEntity.ok(classService.getAll());
    }
}
