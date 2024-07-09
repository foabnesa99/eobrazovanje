package com.ftn.eobrazovanje.service.security;

import com.ftn.eobrazovanje.dao.TokenRepository;
import com.ftn.eobrazovanje.dao.UserRepository;
import com.ftn.eobrazovanje.domain.dto.security.AuthenticationRequest;
import com.ftn.eobrazovanje.domain.dto.security.AuthenticationResponse;
import com.ftn.eobrazovanje.domain.dto.security.CustomUserDetails;
import com.ftn.eobrazovanje.domain.entity.user.Token;
import com.ftn.eobrazovanje.domain.entity.user.User;
import com.ftn.eobrazovanje.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@AllArgsConstructor
@Transactional
public class AuthenticationService {

    private final UserDetailServiceImpl userDetailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        CustomUserDetails user = ((CustomUserDetails) auth.getPrincipal());
        claims.put("fullName", user.getName());
        UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
        var jwtToken = jwtService.generateToken(claims, userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiryDateTime())) {
//            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }
        User user = userService.getByEmail(savedToken.getUser().getEmail());
        user.setActive(true);
        userRepository.save(user);

        savedToken.setValidatedDateTime(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdDateTime(LocalDateTime.now())
                .expiryDateTime(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

//    private void sendValidationEmail(User user) throws MessagingException {
//        var newToken = generateAndSaveActivationToken(user);
//
//        emailService.sendEmail(
//                user.getEmail(),
//                user.getFullName(),
//                EmailTemplateName.ACTIVATE_ACCOUNT,
//                activationUrl,
//                newToken,
//                "Account activation"
//        );
//    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}
