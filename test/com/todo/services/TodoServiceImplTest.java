package com.todo.services;

import com.todo.data.repositories.UserRepository;
import com.todo.dtos.request.LoginRequest;
import com.todo.dtos.request.LogoutRequest;
import com.todo.dtos.request.RegisterRequest;
import com.todo.exceptions.InvalidDetailsException;
import com.todo.exceptions.NoUserExist;
import com.todo.exceptions.UserExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceImplTest {
    @Autowired
    TodoServices todoServices;
    @Autowired
    UserRepository userRepository;

    @AfterEach
    void cleanUp(){
        userRepository.deleteAll();
    }

    @Test void  register_User_Count_Is_One(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");

        todoServices.register(registerRequest);
        assertEquals(1,userRepository.count());
    }

    @Test void registerUser_registerUser_With_Same_Username_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");

        todoServices.register(registerRequest);
        assertThrows(UserExistException.class,()->todoServices.register(registerRequest));
    }

    @Test void registerUser_Login(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findTaskBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findTaskBelongingTo("username").isLogin();
        assertTrue(isLogin);

    }
    @Test void register_Login_With_Invalid_Username_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findTaskBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("name");
        loginRequest.setPassword("password");

        assertThrows(InvalidDetailsException.class,()->todoServices.login(loginRequest));
    }

    @Test void register_Login_With_Invalid_Password_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findTaskBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("word");

        assertThrows(InvalidDetailsException.class,()->todoServices.login(loginRequest));
    }

    @Test void registerUser_Login_Logout(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findTaskBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findTaskBelongingTo("username").isLogin();
        assertTrue(isLogin);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("password");

        todoServices.logout(logoutRequest);
        isLogin = todoServices.findTaskBelongingTo("username").isLogin();
        assertFalse(isLogin);

    }
    @Test void registerUser_Login_Logout_With_Wrong_Username_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findTaskBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findTaskBelongingTo("username").isLogin();
        assertTrue(isLogin);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("name");
        logoutRequest.setPassword("password");

        assertThrows(NoUserExist.class,()->todoServices.logout(logoutRequest));

    }


    @Test void registerUser_Login_Logout_With_Wrong_Password_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findTaskBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findTaskBelongingTo("username").isLogin();
        assertTrue(isLogin);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("word");

        assertThrows(InvalidDetailsException.class,()->todoServices.logout(logoutRequest));

    }


}