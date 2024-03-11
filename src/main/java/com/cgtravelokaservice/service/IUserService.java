package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.UserDTO;
import com.cgtravelokaservice.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    boolean addO2AuthAccount(String username, String providerName);

    boolean addUser(User user, String role);

    boolean updateUserPass(String username, String newPass);

    List<UserDTO> findAll();

    Optional<UserDTO> findById(String email);

    Optional<UserDTO> findValidUserDTOByAccountName(String accountName);

    Optional<User> findValidUserByAccountName(String accountName);

    boolean add(UserDTO userDTO);

    boolean save(User user);

    boolean save(UserDTO userDTO);

    void delete(String email);

    UserDTO toDTO(User user);

    User toUser(UserDTO userDTO);

    boolean checkValidUser(String accountName);

    boolean checkUserExisted(String type, String username);

    Optional<User> findByEmail(String email);

    boolean activeUser(String username);

    boolean checkInValidUser(String accountName);

    boolean checkUserExisted(String accountName);
}
