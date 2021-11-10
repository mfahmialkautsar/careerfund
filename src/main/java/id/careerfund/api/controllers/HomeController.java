package id.careerfund.api.controllers;

import id.careerfund.api.domains.models.DeviceInfo;
import id.careerfund.api.configurations.ResponseDetailConfig;
import id.careerfund.api.domains.models.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController {
    ResponseDetailConfig responseDetailConfig = new ResponseDetailConfig();

    @GetMapping("")
    public ResponseEntity<Object> getDeviceInfo(@RequestHeader("User-Agent") String userAgent, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(new DeviceInfo(responseDetailConfig.getMessageSuccess(), responseDetailConfig.getCodeSuccess(), userAgent, request.getRemoteAddr()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseTemplate(e.getMessage(), responseDetailConfig.getCodeRequired()));
        }
    }
}
