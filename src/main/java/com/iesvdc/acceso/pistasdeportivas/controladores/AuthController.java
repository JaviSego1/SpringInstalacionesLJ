package com.iesvdc.acceso.pistasdeportivas.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesvdc.acceso.pistasdeportivas.componentes.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        final UserDetails userDetails = User.withUsername(request.getUsername()).password(request.getPassword()).build();
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getHello(@RequestParam String param) {
        
        return ResponseEntity.ok(new String("hello"));
    }

    @GetMapping("/test")
    public ResponseEntity<String> getTest(@RequestParam String param) {
        
        return ResponseEntity.ok(new String("test"));
    }
    
}

class AuthRequest {
    private String username;
    private String password;
    

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}

class AuthResponse {
    private String jwt;
    public AuthResponse(String jwt) { this.jwt = jwt; }
    public String getJwt() { return jwt; }
}
