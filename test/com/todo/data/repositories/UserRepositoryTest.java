package com.todo.data.repositories;

import com.todo.data.models.User;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @AfterEach
    void  cleanUp(){
        userRepository.deleteAll();
    }

    @Test void save_One_Count_Is_One(){
        User user = new User();
        userRepository.save(user);
        assertEquals(1,userRepository.count());
    }

}