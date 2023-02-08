package com.pulidodev.security.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pulidodev.security.config.jwt.JwtProvider;
import com.pulidodev.security.dto.AuthenticationRequestDto;
import com.pulidodev.security.dto.AuthenticationResponseDto;
import com.pulidodev.security.dto.RegisterRequestDto;
import com.pulidodev.security.model.User;
import com.pulidodev.security.repository.UserRepository;
import com.pulidodev.security.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

        private final UserRepository repository;

        private final PasswordEncoder passwordEncoder;

        private final JwtProvider jwtProvider;

        private final AuthenticationManager authenticationManager;

        @Override
        public AuthenticationResponseDto register(RegisterRequestDto request) {
                var user = User.builder()
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .build();

                repository.save(user);

                var jwt = jwtProvider.generateToken(user);

                return AuthenticationResponseDto.builder()
                                .token(jwt)
                                .build();
        }

        @Override
        public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                                                request.getPassword()));

                var user = repository.findByEmail(request.getEmail()).orElseThrow();
                var jwt = jwtProvider.generateToken(user);

                return AuthenticationResponseDto.builder()
                                .token(jwt)
                                .build();
        }
}
