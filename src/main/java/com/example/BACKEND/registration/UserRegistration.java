package com.example.BACKEND.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Registration;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class UserRegistration {

    private RegistrationService registrationService;

    @PatchMapping
    public String register(@RequestBody RegistrationRequest request){

        return registrationService .register(request);
    }
}
