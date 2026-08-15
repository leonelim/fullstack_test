package com.pistophone.factory.controller;

import com.pistophone.factory.config.UserDetailsImpl;
import com.pistophone.factory.dto.LoginDto;
import com.pistophone.factory.model.User;
import com.pistophone.factory.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Auth {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(Auth.class);

    Auth(AuthenticationManager authenticationManager,
         UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto,
                                   HttpServletRequest request) {
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginDto.id(), loginDto.password());
        Authentication response = this.authenticationManager.authenticate(authentication);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(response);
        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                securityContext
        );
        logger.debug("auth: {}", response);
        return ResponseEntity.ok().build();
        /*
        logger.debug("exception!");
        Authentication authentication = new UsernamePasswordAuthenticationToken(Long.toString(loginDto.getId()), loginDto.getPassword());
        logger.debug("exception!");
        Authentication auth = authenticationManager.authenticate(authentication);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        if (auth.isAuthenticated()) {
            logger.debug("authenticated {}", securityContext);
            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    securityContext
            );
            return ResponseEntity.ok().build();
        }
        logger.debug("auth failed {}", securityContext);
        return ResponseEntity.notFound().build();
         */
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody LoginDto loginDto) {
        User user = new User(loginDto.id(), loginDto.password());
        userRepository.saveUser(user);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody String password,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                            HttpServletRequest request) {
        logger.debug("new password for {}: {}", userDetails.getId(), password);
        userRepository.changePassword(userDetails.getId(), password);
        var token = UsernamePasswordAuthenticationToken.unauthenticated(userDetails.getId(), password);
        Authentication authentication = authenticationManager.authenticate(token);
        logger.debug(authentication.toString());
        HttpSession session = request.getSession();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                securityContext
        );
        logger.debug("new password: {}", password);
        return ResponseEntity.ok().build();
    }
}
