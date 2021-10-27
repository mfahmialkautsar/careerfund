package id.careerfund.api.controllers;

import id.careerfund.api.domains.models.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("")
    public ResponseEntity<Object> get(@RequestHeader("User-Agent") String userAgent) {
        Response<String> response = new Response<>("success", String.format("Your are from: %s", userAgent));
        return ResponseEntity.ok(response);
    }
}
