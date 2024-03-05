package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.exceptions.GlobalExceptions;
import com.ecommerce.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceCrudImplTest {

    private UserServiceCrud userServiceCrud;
    private PasswordEncoder password;


    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userServiceCrud = new UserServiceCrudImpl(userRepository,password);
    }


    @Test
    void findAll() {
        userServiceCrud.findAll();
        verify(userRepository).findAll();
    }

    @Test
    void canSave() {
        User user = new User();
        user.setFullName("irem veli");
        user.setEmail("irem@gmail.com");
        user.setPassword("irem123");
        userServiceCrud.save(user);
        verify(userRepository).save(user);
    }




    @Test
    void delete() {
        User user = new User();
        user.setId(1L);
        user.setEmail("irem@gmail.com");
        user.setFullName("irem veli");
        user.setPassword("irem123");

        given(userRepository.findById(Long.valueOf(1L))).willReturn(Optional.of(user));

        User deletedUser = userServiceCrud.delete(user.getId());

        verify(userRepository).delete(user);
        assertEquals("irem veli",deletedUser.getFullName());
    }
}