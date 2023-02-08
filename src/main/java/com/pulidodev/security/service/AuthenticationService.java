package com.pulidodev.security.service;

import com.pulidodev.security.dto.AuthenticationRequestDto;
import com.pulidodev.security.dto.AuthenticationResponseDto;
import com.pulidodev.security.dto.RegisterRequestDto;

public interface AuthenticationService {

    public AuthenticationResponseDto register(RegisterRequestDto request);

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request);
}
