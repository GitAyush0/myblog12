package com.blogapp12.controller;

import com.blogapp12.entity.Role;
import com.blogapp12.entity.User;
import com.blogapp12.payload.LoginDto;
import com.blogapp12.payload.SignUp;
import com.blogapp12.repository.RoleRepository;
import com.blogapp12.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //http://localhost:8080/api/auth/sign-up
    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody SignUp signUp){

        if(userRepository.existsByEmail(signUp.getEmail())){
            return new ResponseEntity<>("Email id is already registered" , HttpStatus.IM_USED);
        }
        if(userRepository.existsByUsername(signUp.getUsername())){
            return new ResponseEntity<>("User name is already registered" , HttpStatus.IM_USED);
        }

        User user = new User();
        user.setName(signUp.getName());
        user.setEmail(signUp.getEmail());
        user.setUsername(signUp.getUsername());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));

        Role roles = roleRepository.findByName(signUp.getUserRole()).get();

        Set<Role> convertRoleTOSet = new HashSet<>() ;
        convertRoleTOSet.add(roles);

        user.setRoles(convertRoleTOSet);

        userRepository.save(user);

        return new ResponseEntity<>("User registered" , HttpStatus.CREATED);


    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody LoginDto loginDto){

        //this will pass the expected value to loadByUsername and compare with actual value
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        //this will declare the comparison as pass or fail
        //if fail it will throw error
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //after login we will create a session variable
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return new ResponseEntity<>("Login successful..." , HttpStatus.OK);


    }

}
