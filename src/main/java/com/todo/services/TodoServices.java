package com.todo.services;

import com.todo.data.models.User;
import com.todo.dtos.request.LoginRequest;
import com.todo.dtos.request.LogoutRequest;
import com.todo.dtos.request.RegisterRequest;

public interface TodoServices {
    User register(RegisterRequest registerRequest);
    void login(LoginRequest loginRequest);
    User findTaskBelongingTo(String username);
    void logout(LogoutRequest logoutRequest);
}
