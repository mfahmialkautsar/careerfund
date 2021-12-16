package id.careerfund.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import id.careerfund.api.domains.models.responses.DeviceInfo;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController extends HandlerController {
    @GetMapping("")
    public ResponseEntity<DeviceInfo> getDeviceInfo(@RequestHeader("User-Agent") String userAgent,
            HttpServletRequest request) {
        return ResponseEntity.ok(new DeviceInfo(userAgent, request.getRemoteAddr()));
    }
}
