package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.models.MyClass;
import id.careerfund.api.domains.models.MyClasses;
import id.careerfund.api.services.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping("/classes")
    public ResponseEntity<List<Class>> getClasses() {
        return ResponseEntity.ok(classService.getAll());
    }

    @GetMapping("/my/classes")
    public ResponseEntity<MyClasses> getMyClasses(Principal principal) {
        List<MyClass> classes = Arrays.asList(
                new MyClass("Binar Academy", "Back End Development", "https://kerjabilitas.com/user_image/user2/logo_7b6caab85699ca72e06917e9bad7512c.png", LocalDate.of(2021, 4, 1), LocalDate.of(2021, 6, 30), 8.7),
                new MyClass("Apple Developer Academy", "World Class Developer", "https://logique.s3.ap-southeast-1.amazonaws.com/2020/11/apple-developer-academy.jpg", LocalDate.of(2022, 2, 1), LocalDate.of(2022, 12, 31), null),
                new MyClass("Google Developers Kejar", "Android", "https://www.its.ac.id/matematika/wp-content/uploads/sites/42/2019/05/google-developers.jpg", LocalDate.of(2019, 6, 1), LocalDate.of(2019, 12, 31), 9.8)
        );
        return ResponseEntity.ok(new MyClasses(classes));
    }
}
