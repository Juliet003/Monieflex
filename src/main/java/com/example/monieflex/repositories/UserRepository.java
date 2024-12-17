package com.example.monieflex.repositories;

import com.example.monieflex.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional <User> findByEmail(String email);
//   Optional<User> findOtpByEmail(String email);

   @Query("SELECT u.otp FROM User u WHERE u.email = :email")
   String findOtpByEmail(@Param("email") String email);

   @Modifying
   @Transactional
   @Query("UPDATE User u SET u.isVerified = true WHERE u.email = :email")
   void markUserAsVerified(@Param("email") String email);

}
