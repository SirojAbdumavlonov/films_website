package com.example.backend_prj.repository;

import com.example.backend_prj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByUserId(int id);


    Optional<User> findByEmail(String email);
}
