package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM User WHERE (username = :username OR email = :username OR phone = :username) AND is_active = false", nativeQuery = true)
    Optional<User> loadInvalidUser(@Param("username") String username);

    @Query(value = "SELECT * FROM User WHERE (username = :username OR email = :username OR phone = :username) AND is_active = true", nativeQuery = true)
    Optional<User> loadValidUser(@Param("username") String username);

    @Query(value = "SELECT * FROM User where username= :username or email = :email or phone = :phone and is_active" +
            " " +
            "= true", nativeQuery = true)
    Optional<User> findValidUserByUsernameOrEmailOrPhone(String username, String email, String phone);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
