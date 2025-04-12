package com.hlp.api.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hlp.api.domain.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
