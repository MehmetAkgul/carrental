package com.lecture.carrental.service;


import com.lecture.carrental.domain.Role;
import com.lecture.carrental.domain.User;
import com.lecture.carrental.domain.enumeration.UserRole;
import com.lecture.carrental.exception.BadRequestException;
import com.lecture.carrental.exception.ConflictException;
import com.lecture.carrental.exception.ResourcesNotFoundException;
import com.lecture.carrental.repository.RoleRepository;
import com.lecture.carrental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public void register(User user) throws BadRequestException {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Error: Email is already in use");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //  user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role customerRole = roleRepository.findByName(UserRole.ROLE_CUSTOMER)
                .orElseThrow(() -> new ResourcesNotFoundException("Error: Role is Not Found"));

        roles.add(customerRole);
        user.setRoles(roles);

        userRepository.save(user);


    }

}
