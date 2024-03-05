package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.exceptions.GlobalExceptions;
import com.ecommerce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceCrudImpl implements UserServiceCrud {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceCrudImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser=userRepository.findById(id);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new GlobalExceptions("User is not found with this id: " + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public User save(User user) {
        Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new GlobalExceptions("This email is already in use", HttpStatus.CONFLICT);
        }
        return userRepository.save(user);
    }

    @Override
    public User delete(Long id) {
        User user=findById(id);
        if (user!=null){
            userRepository.delete(user);
            return user;
        }
        throw new GlobalExceptions("User is not found with this id: " + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public User update(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            throw new GlobalExceptions("User is not found with this id: " + id, HttpStatus.NOT_FOUND);
        }

        if (updatedUser.getFullName() != null) {
            existingUser.setFullName(updatedUser.getFullName());
        }
        if (updatedUser.getEmail() != null) {
            User existingUserWithEmail = userRepository.findUserByEmail(updatedUser.getEmail()).orElse(null);
            if (existingUserWithEmail != null && !existingUserWithEmail.getId().equals(id)) {
                throw new GlobalExceptions("This email is already in use", HttpStatus.BAD_REQUEST);
            }
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null) {
            // Parolanın hashlenmiş haliyle güncellenmesi
            String hashedPassword = passwordEncoder.encode(updatedUser.getPassword());
            existingUser.setPassword(hashedPassword);
        }

        User updatedUserEntity = userRepository.save(existingUser);
        return updatedUserEntity;
    }

}
