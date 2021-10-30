package id.careerfund.api.controllers;

import id.careerfund.api.domains.models.DeviceInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController {
    @GetMapping("")
    public ResponseEntity<DeviceInfo> getDeviceInfo(@RequestHeader("User-Agent") String userAgent, HttpServletRequest request) {
        return ResponseEntity.ok(new DeviceInfo(userAgent, request.getRemoteAddr()));
    }
}
