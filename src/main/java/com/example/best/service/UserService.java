package com.example.best.service;

import com.example.best.model.Roles;
import com.example.best.model.Users;
import com.example.best.repository.RoleRepository;
import com.example.best.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public void saveUser(Users adminDto) {
        Users user = new Users();
        user.setName(adminDto.getName());
        user.setEmail(adminDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(adminDto.getPassword()));

        Roles role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    public List<Users> findAllUsers() {
        List<Users> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private Users mapToUserDto(Users user){
        Users adminDto = new Users();
      adminDto.setName(user.getName());
        adminDto.setEmail(user.getEmail());
        return adminDto;
    }

    private Roles checkRoleExist(){
        Roles role = new Roles();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    public Users findByUsername(String name) {
        return  userRepository.findByName(name);
    }
}
