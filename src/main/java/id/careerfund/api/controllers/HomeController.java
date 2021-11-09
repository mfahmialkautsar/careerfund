package id.careerfund.api.controllers;

import id.careerfund.api.configurations.jwt.JwtService;
import id.careerfund.api.domains.models.AuthRequest;
import id.careerfund.api.domains.models.DeviceInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public HomeController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("")
    public ResponseEntity<DeviceInfo> getDeviceInfo(@RequestHeader("User-Agent") String userAgent, HttpServletRequest request) {
        return ResponseEntity.ok(new DeviceInfo(userAgent, request.getRemoteAddr()));
    }

    @RequestMapping("/hi")
    public String hello() {
        return "Hi World, Spring Boot";
    }

    @PostMapping("/authenticate")
    public String generateToken (@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new Exception("invalid");
        }

        return jwtService.generateToken(authRequest.getEmail());
    }
}
