package com.example.TimeAttendanceAPI.security.service;

import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.model.AttendanceCache;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.ERole;
import com.example.TimeAttendanceAPI.repository.AttendanceCacheRepository;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import com.example.TimeAttendanceAPI.security.JwtResponse;
import com.example.TimeAttendanceAPI.security.RegisterRequest;
import com.example.TimeAttendanceAPI.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AttendanceCacheRepository attendanceCacheRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public boolean createUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return false;
        }

        //After validation, create new account
        User user = new User(registerRequest.getUsername(), passwordEncoder.encode(registerRequest.getPassword()));

        ERole assignedRole;

        if (StringUtils.isNotBlank(registerRequest.getRole())) {
            if (EnumUtils.isValidEnum(ERole.class, registerRequest.getRole())) {
                assignedRole = ERole.valueOf(registerRequest.getRole());
            } else {
                throw new RuntimeException("Role " + "\'" + registerRequest.getRole() + "\'" + " is not found.");
            }
        } else {
            throw new RuntimeException("Role is empty!");
        }

        user.setRole(assignedRole);
        User result = userRepository.save(user);

        AttendanceCache newCache = new AttendanceCache();
        newCache.setUser(result);
        newCache.setCheckIn(false);
        attendanceCacheRepository.save(newCache);

        return true;
    }

    @Override
    public JwtResponse login(UserDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return new JwtResponse(jwt, userDetails.getUsername());
    }
}
