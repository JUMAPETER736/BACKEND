package com.example.BACKEND.registration;


import com.example.BACKEND.appuser.AppUser;
import com.example.BACKEND.appuser.AppUserRole;
import com.example.BACKEND.appuser.AppUserService;
import com.example.BACKEND.email.EmailSender;
import com.example.BACKEND.registration.Token.ConfirmationToken;
import com.example.BACKEND.registration.Token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationService {

    private  final AppUserService appUserService;
    private final Emailvalidator emailvalidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;


    public String register(RegistrationRequest request) throws IllegalAccessException {

        boolean isValidEmail = emailvalidator.test(request.getEmail());

        if(!isValidEmail){

            throw new IllegalAccessException("Email is NOT valid...");
        }

        String token = appUserService.signUpUser(

                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )

        );

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;

        emailSender.send(request.getEmail(),
                        buildEmail(request.getFirstName(), link));
        return token;
    }

    private String buildEmail(String name, String link) {

        return "";
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));


        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }


        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {

            throw new IllegalStateException("token expired");
        }


        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }





}
