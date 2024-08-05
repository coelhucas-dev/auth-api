package com.lucascoelho.auth_api.domain.users.profile;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "users_profile")
public class UsersProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private UUID users_fk;

    @NotNull
    private String firstName;
    private String lastName;

    @Email
    private String email;
}
