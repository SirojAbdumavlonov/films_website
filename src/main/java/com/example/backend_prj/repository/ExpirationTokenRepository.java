package com.example.backend_prj.repository;

import com.example.backend_prj.entity.ExpirationToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpirationTokenRepository extends JpaRepository<ExpirationToken, Long> {
        ExpirationToken findByTokenId(int id);

        @Query(
                value = "select e from ExpirationToken e where e.user.userId = ?1"
        )
        ExpirationToken findByUserId(int id);
        @Transactional(rollbackOn = Exception.class)
        @Modifying
        @Query(
                value = "update ExpirationToken e set e.logged = false where e.user.userId = ?1"
        )
        void tokenLoggedToZero(int id);
        @Transactional(rollbackOn = Exception.class)
        @Modifying
        @Query(
                value = "update ExpirationToken e set e.logged = true where e.user.userId = ?1"
        )
        void tokenLoggedToOne(int id);
}
