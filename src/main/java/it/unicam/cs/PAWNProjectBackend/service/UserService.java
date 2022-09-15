package it.unicam.cs.PAWNProjectBackend.service;

import it.unicam.cs.PAWNProjectBackend.model.Role;
import it.unicam.cs.PAWNProjectBackend.model.User;
import it.unicam.cs.PAWNProjectBackend.repository.RoleRepository;
import it.unicam.cs.PAWNProjectBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    private final UserRepository userRepository;

    public User registerNewUser(User user){

        Role role = roleRepository.findAll().stream().filter(r -> r.getRoleName().equals("User")).findFirst().get();

        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return this.userRepository.save(user);
    }


    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }

}
