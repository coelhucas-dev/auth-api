package com.lucascoelho.auth_api.repositories.users.profile;

import com.lucascoelho.auth_api.domain.users.profile.UsersProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersProfileRepository extends JpaRepository<UsersProfile, Long> {}
