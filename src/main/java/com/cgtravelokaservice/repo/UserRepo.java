package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM User where ( username= :username or email = :username or phone = :username) and " +
            "is_active = false", nativeQuery = true)
    Optional<User> loadInvalidUser(@Param("username") String username);

    @Query(value = "SELECT * FROM User where ( username= :username or email = :username or phone = :username ) and " +
            "is_active = true", nativeQuery = true)
    Optional<User> loadValidUser(@Param("username") String username);

    @Query(value = "SELECT * FROM User where username= :username or email = :email or phone = :phone and is_active" +
            " " +
            "= true", nativeQuery = true)
    Optional<User> findValidUserByUsernameOrEmailOrPhone(String username, String email, String phone);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
