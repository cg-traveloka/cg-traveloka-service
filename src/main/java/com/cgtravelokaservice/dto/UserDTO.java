package com.cgtravelokaservice.dto;

import com.cgtravelokaservice.entity.user.UserProvider;
import com.cgtravelokaservice.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private boolean enable;
    private boolean isActive;
    private Set<UserRole> userRoles;
    private Set<UserProvider> userProvider;
}
