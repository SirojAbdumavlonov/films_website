package com.example.backend_prj.repository;

import com.example.backend_prj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    int findByFullname(String fullName);

    User findByEmailAndPassword(String email, String password);

    User findByUserId(int id);

}
