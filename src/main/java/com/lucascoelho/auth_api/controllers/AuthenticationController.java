package com.lucascoelho.auth_api.controllers;

import com.lucascoelho.auth_api.domain.users.Users;
import com.lucascoelho.auth_api.domain.users.UsersRole;
import com.lucascoelho.auth_api.domain.users.profile.UsersProfile;
import com.lucascoelho.auth_api.dto.auth.LoginRequestDTO;
import com.lucascoelho.auth_api.dto.auth.LoginResponseDTO;
import com.lucascoelho.auth_api.dto.auth.RegisterRequestDTO;
import com.lucascoelho.auth_api.dto.auth.RegisterResponseDTO;
import com.lucascoelho.auth_api.exceptions.users.UserAlreadyExistsException;
import com.lucascoelho.auth_api.infra.services.TokenService;
import com.lucascoelho.auth_api.repositories.users.UsersRepository;
import com.lucascoelho.auth_api.repositories.users.profile.UsersProfileRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UsersProfileRepository usersProfileRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO body) {
        Users user = this.usersRepository.findByUsername(body.username()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponseDTO(user.getUsername(), token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        Optional<Users> user = this.usersRepository.findByUsername(body.username());
        if (user.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        Users newUser = new Users();
        newUser.setUsername(body.username());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setRole(UsersRole.MEMBER);
        Users savedUser = this.usersRepository.save(newUser);

        UsersProfile newUserProfile = new UsersProfile();
        newUserProfile.setEmail(body.email());
        newUserProfile.setFirstName(body.firstName());
        newUserProfile.setLastName(body.lastName());
        newUserProfile.setUsers_fk(savedUser.getId());
        usersProfileRepository.save(newUserProfile);

        URI uriLocation = URI.create("/auth/register");

        String token = this.tokenService.generateToken(newUser);

        return ResponseEntity.created(uriLocation).body(new RegisterResponseDTO(savedUser.getId(), savedUser.getUsername(), token));
    }
}
