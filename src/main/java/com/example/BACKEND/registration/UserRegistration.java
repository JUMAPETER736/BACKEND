package com.example.BACKEND.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Registration;

@RestController
@RequestMapping(path = "api/v1/students")
@AllArgsConstructor
public class UserRegistration {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request)
            throws IllegalAccessException {

        return registrationService.register(request);

    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token){

        return  registrationService.confirmToken(token);
    }


}
