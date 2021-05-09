package com.example.TimeAttendanceAPI.security.service;

import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.model.Role;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.ERole;
import com.example.TimeAttendanceAPI.repository.RoleRepository;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import com.example.TimeAttendanceAPI.security.JwtResponse;
import com.example.TimeAttendanceAPI.security.RegisterRequest;
import com.example.TimeAttendanceAPI.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public boolean createUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return false;
        }

        //After validation, create new account
        User user = new User(registerRequest.getUsername(), passwordEncoder.encode(registerRequest.getPassword()));

        Role assignedRole;

        if (!StringUtils.isNotBlank(registerRequest.getRole().name())) {
            assignedRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Role not found!"));
        } else {
            assignedRole = roleRepository.findByName(registerRequest.getRole())
                    .orElseThrow(() -> new RuntimeException("Role not found!"));
        }

        user.setRole(assignedRole);
        userRepository.save(user);

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
