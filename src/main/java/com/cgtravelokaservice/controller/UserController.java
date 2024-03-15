package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.UserDTO;
import com.cgtravelokaservice.dto.request.SetRoleRequest;
import com.cgtravelokaservice.entity.user.User;
import com.cgtravelokaservice.entity.user.UserRole;
import com.cgtravelokaservice.repo.RoleRepo;
import com.cgtravelokaservice.repo.UserRoleRepo;
import com.cgtravelokaservice.service.implement.UserService;
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

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Autowired
    private RoleRepo roleRepo;


    /* ---------------- GET ALL USER ------------------------ */

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    /* ---------------- GET USER BY ID ------------------------ */
    @RequestMapping(value = "/users/{email}", method = RequestMethod.GET)
    public @ResponseBody UserDTO getUserById(@PathVariable String email) {
        Optional<UserDTO> user = userService.findById(email);
        return user.orElse(null);
    }


    /* ---------------- DELETE USER ------------------------ */
    @RequestMapping(value = "/users/{email}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserById(@PathVariable String email) {
        userService.delete(email);
        return new ResponseEntity<>("Đã xoá user!", HttpStatus.OK);
    }


    @PostMapping("/users/setRole")
    public ResponseEntity<?> setRole(@RequestBody SetRoleRequest request) {
        Optional<User> user = userService.findValidUserByAccountName(request.getUsername());
        if (user.isPresent()) {
            Set<UserRole> userRoles = user.get().getUserRoles();
            for (UserRole userRole : userRoles) {
                if (userRole.getRole().getName().equals(request.getNewRole().toUpperCase())) {
                    return ResponseEntity.badRequest().body("User hiện đã có phân quyền này");
                }
            }
            try {
                UserRole userRole =
                        UserRole.builder().user(user.get()).role(roleRepo.findByName(request.getNewRole().toUpperCase()).get()).build();
                userRoleRepo.save(userRole);
                return ResponseEntity.ok("Cập nhật phân quyền thành công");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Cập nhật phân quyền thất bại");
            }

        } else {
            return ResponseEntity.status(406).body("User is not exist or user is not active");
        }
    }

}