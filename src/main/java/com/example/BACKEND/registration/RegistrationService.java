package com.example.BACKEND.registration;


import com.example.BACKEND.appuser.AppUser;
import com.example.BACKEND.appuser.AppUserRole;
import com.example.BACKEND.appuser.AppUserService;
import com.example.BACKEND.registration.Token.ConfirmationToken;
import com.example.BACKEND.registration.Token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private  final AppUserService appUserService;
    private final Emailvalidator emailvalidator;
    private final ConfirmationTokenService confirmationTokenService;


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


        return token;
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
