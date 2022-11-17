package com.unittest.unittest.repositories;


import com.unittest.unittest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
