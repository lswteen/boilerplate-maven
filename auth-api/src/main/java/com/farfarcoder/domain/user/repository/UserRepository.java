package com.farfarcoder.domain.user.repository;

import com.farfarcoder.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findOneWithUserByUserId(String userId);
}
