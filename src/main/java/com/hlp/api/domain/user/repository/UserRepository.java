package com.hlp.api.domain.user.repository;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.user.model.User;

public interface UserRepository extends Repository<User, Long> {
}
