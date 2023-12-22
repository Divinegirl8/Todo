package com.todo.utils;

import com.todo.data.models.User;
import com.todo.dtos.request.RegisterRequest;

public class Mapper {
    public static User map(String userId,String username,String password){
        User user = new User();
        user.setUserId(userId);
        user.setUserName(username);
        user.setPassword(password);
        return user;
    }
}
