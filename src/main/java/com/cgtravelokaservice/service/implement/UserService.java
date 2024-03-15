package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.UserDTO;
import com.cgtravelokaservice.entity.user.Customer;
import com.cgtravelokaservice.entity.user.Partner;
import com.cgtravelokaservice.entity.user.Role;
import com.cgtravelokaservice.entity.user.User;
import com.cgtravelokaservice.entity.user.UserPrinciple;
import com.cgtravelokaservice.entity.user.UserRole;
import com.cgtravelokaservice.repo.CustomerRepo;
import com.cgtravelokaservice.repo.PartnerRepo;
import com.cgtravelokaservice.repo.RoleRepo;
import com.cgtravelokaservice.repo.UserRepo;
import com.cgtravelokaservice.service.IUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "users")
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private CustomerRepo customerRepo;


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public boolean addO2AuthAccount(String username, String providerName) {
        try {
            User user = User.builder()
                    .username(username)
                    .email(username)
                    .enable(true)
                    .isActive(true).build();
            Role role = roleRepo.findByName("ROLE_CUSTOMER").orElse(null);
            UserRole userRole = UserRole.builder().user(user).role(role).build();

            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(userRole);
            user.setUserRoles(userRoles);
            userRepo.save(user);
            return true;
        } catch (Exception e) {
            System.err.println("Can not add o2auth account" + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean addUser(User user, String role) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(false);
            user.setEnable(false);
            Set<UserRole> userRoles = new HashSet<>();
            UserRole userRole =
                    UserRole.builder().user(user).role(roleRepo.findByName(role.toUpperCase()).get()).build();
            userRoles.add(userRole);
            user.setUserRoles(userRoles);
            userRepo.save(user);
            if (role.equalsIgnoreCase("ROLE_PARTNER")) {
                Partner partner = new Partner();
                partner.setUser(user);
                partnerRepo.save(partner);
            }
            if (role.equalsIgnoreCase("ROLE_CUSTOMER")) {
                Customer customer = new Customer();
                customer.setUser(user);
                customerRepo.save(customer);
            }
            return true;

        } catch (Exception e) {
            System.out.println("Error during saving user -> Message {} " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateUserPass(String username, String newPass) {
        try {
            Optional<User> user = userRepo.loadValidUser(username);
            if (user.isPresent()) {
                user.get().setPassword(passwordEncoder.encode(newPass));
                userRepo.save(user.get());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error during updating user: " + e.getMessage());
            return false;
        }
    }

    @Override
    @Cacheable(key = "'allUsers'")
    public List<UserDTO> findAll() {
        Type targetListType = new TypeToken<List<UserDTO>>() {
        }.getType();
        return modelMapper.map(userRepo.findAll(), targetListType);

    }


    @Override
    @Cacheable(key = "#email")
    public Optional<UserDTO> findById(String email) {
        Optional<User> userOptional = userRepo.findById(email);
        return userOptional.map(user -> modelMapper.map(user, UserDTO.class));
    }


    @Override
    public Optional<UserDTO> findValidUserDTOByAccountName(String accountName) {
        Optional<User> userOptional = userRepo.loadValidUser(accountName);//true
        return userOptional.map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Optional<User> findValidUserByAccountName(String accountName) {
        return userRepo.loadValidUser(accountName);
    }


    @Override
    public boolean add(UserDTO userDTO) {
        if (userRepo.findValidUserByUsernameOrEmailOrPhone(userDTO.getUsername(), userDTO.getEmail(),
                userDTO.getPhone()).isEmpty()) {
            User user = User.builder().username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .email(userDTO.getEmail())
                    .phone(userDTO.getPhone()).build();
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.saveAndFlush(user);
        return true;
    }

    @Override
    public boolean save(UserDTO userDTO) {
        try {
            User user = toUser(userDTO);
            save(user);
            return true;
        } catch (Exception e) {
            System.out.println("Error during saving user " + e.getMessage());
            return false;
        }
    }

    @Override
    @CacheEvict(value = "users", key = "#email")
    public void delete(String email) {
        userRepo.deleteById(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepo.loadValidUser(username).isPresent() ?
                userRepo.loadValidUser(username) : userRepo.loadInvalidUser(username);
        return userOptional.map(UserPrinciple::build).orElse(null);
    }

    @Override
    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User toUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public boolean checkValidUser(String accountName) {
        return userRepo.loadValidUser(accountName).isPresent();
    }

    @Override
    public boolean checkUserExisted(String type, String username) {
        Optional<User> user = Optional.empty();
        switch (type.toUpperCase()) {
            case "USERNAME":
                return checkUserExisted(username);
            case "EMAIL":
                user = userRepo.findByEmail(username);
                break;
            case "PHONE":
                user = userRepo.findByPhone(username);
                break;
        }
        return user.isPresent();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Scheduled(fixedRate = 5000)
    @CacheEvict(value = "users", allEntries = true)
    public void clearAllCache() {

    }

    @Override
    public boolean activeUser(String username) {
        try {
            Optional<User> user = userRepo.loadInvalidUser(username);
            if (user.isPresent()) {
                user.get().setActive(true);
                userRepo.save(user.get());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error during active user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean checkInValidUser(String accountName) {
        return userRepo.loadInvalidUser(accountName).isPresent();
    }

    @Override
    public boolean checkUserExisted(String accountName) {
        return checkInValidUser(accountName) || checkValidUser(accountName);
    }

}