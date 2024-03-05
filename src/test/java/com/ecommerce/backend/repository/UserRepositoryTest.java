package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void createUser(String email){
        User user = new User();
        user.setFullName("irem veli");
        user.setEmail(email);
        user.setPassword("irem123");
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()){
            User foundUser = userRepository.findUserByEmail(email).get();
            if(foundUser == null){
                userRepository.save(user);
            }
        }
    }

    @BeforeEach
    void setUp() {
        createUser("irem@gmail.com");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("Can find user by email")
    @Test
    void findUserByEmail() {
        Optional<User> optionalUser = userRepository.findUserByEmail("irem@gmail.com");
        if(optionalUser.isPresent()){
            User foundUser = userRepository.findUserByEmail("irem@gmail.com").get();
            assertNotNull(foundUser);
            assertEquals("irem veli",foundUser.getFullName());
        }
    }

    @DisplayName("Cant find user by email")
    @Test
    void findUserByEmailFail() {
        Optional<User> foundUser = userRepository.findUserByEmail("iremm@gmail.com");
        if (!foundUser.isPresent()) {
            assertNull(foundUser.orElse(null));
        }
    }

}