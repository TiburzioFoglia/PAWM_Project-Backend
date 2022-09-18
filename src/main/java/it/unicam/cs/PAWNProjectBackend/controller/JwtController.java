package it.unicam.cs.PAWNProjectBackend.controller;

import it.unicam.cs.PAWNProjectBackend.model.JwtRequest;
import it.unicam.cs.PAWNProjectBackend.model.JwtResponse;
import it.unicam.cs.PAWNProjectBackend.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/authenticate")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{
        return jwtService.createJwtToken(jwtRequest);
    }
}
