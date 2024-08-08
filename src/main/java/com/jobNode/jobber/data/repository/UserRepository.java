package com.jobNode.jobber.data.repository;

import com.jobNode.jobber.data.models.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
