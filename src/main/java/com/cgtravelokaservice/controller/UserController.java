package com.codegym.controller;

import com.codegym.model.dto.UserDTO;
import com.codegym.model.entity.Role;
import com.codegym.model.request.SetRoleRequest;
import com.codegym.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")

public class UserController {


    @Autowired
    private UserService userService;


    /* ---------------- GET ALL USER ------------------------ */

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    /* ---------------- GET USER BY ID ------------------------ */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public @ResponseBody UserDTO getUserById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.findById(id);
        return user.orElse(null);
    }

    /* ---------------- CREATE NEW USER ------------------------ */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody UserDTO user) {
        if (userService.add(user)) {
            return new ResponseEntity<>("Created!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User Existed!", HttpStatus.BAD_REQUEST);
        }
    }

    /* ---------------- DELETE USER ------------------------ */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>("Deleted!", HttpStatus.OK);
    }


    @PostMapping("/setRole")
    public ResponseEntity<?> check(@RequestBody SetRoleRequest request) {
        Optional<UserDTO> userDTO = userService.findValidUserDTOByAccountName(request.getUsername());
        if (userDTO.isPresent()) {
            Set<Role> roles = userDTO.get().getRoles();
            for (Role role : roles) {
                if (role.getName().equals(request.getNewRole())) {
                    return ResponseEntity.badRequest().body("User already have this role");
                }
            }
            return ResponseEntity.ok("Update role success");
        } else {
            return ResponseEntity.status(406).body("User is not exist or user is not active");
        }
    }

}