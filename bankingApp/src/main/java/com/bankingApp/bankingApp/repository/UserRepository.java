package com.bankingApp.bankingApp.repository;

import com.bankingApp.bankingApp.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @EntityGraph(attributePaths = "account")
    Optional<User> findByUsername(String username);








}
