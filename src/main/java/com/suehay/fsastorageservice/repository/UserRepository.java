package com.suehay.fsastorageservice.repository;

import com.suehay.fsastorageservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}