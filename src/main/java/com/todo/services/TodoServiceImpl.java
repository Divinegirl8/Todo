package com.todo.services;

import com.todo.data.models.User;
import com.todo.data.repositories.TaskRepository;
import com.todo.data.repositories.UserRepository;
import com.todo.dtos.request.LoginRequest;
import com.todo.dtos.request.LogoutRequest;
import com.todo.dtos.request.RegisterRequest;
import com.todo.exceptions.InvalidDetailsException;
import com.todo.exceptions.NoUserExist;
import com.todo.exceptions.NotLoginException;
import com.todo.exceptions.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.todo.utils.Mapper.*;

@Service
public class TodoServiceImpl implements TodoServices{
    @Autowired
    TaskService taskService;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public User register(RegisterRequest registerRequest) {
        if (userExist(registerRequest.getUsername())){ throw new UserExistException(registerRequest.getUsername() + " already exists");
        }
        User user = map("UID" + (userRepository.count() + 1),registerRequest.getUsername(),registerRequest.getPassword());
        userRepository.save(user);
        return user;
    }

    @Override
    public void login(LoginRequest loginRequest) {
        User user = userRepository.findUserByUserName(loginRequest.getUsername());

        if (!userExist(loginRequest.getUsername())) throw new InvalidDetailsException();
        if (!user.getPassword().equals(loginRequest.getPassword())) throw new InvalidDetailsException();

        user.setLogin(true);
        userRepository.save(user);

    }



    @Override
    public User findTaskBelongingTo(String username) {
        return userRepository.findUserByUserName(username);
    }



    @Override
    public void logout(LogoutRequest logoutRequest) {
     User user = userRepository.findUserByUserName(logoutRequest.getUsername());

     if (user == null) throw new NoUserExist(logoutRequest.getUsername() + " not found");

     if (!user.isLogin()) throw new NotLoginException("You must login to perform this action");

     if (!user.getPassword().equals(logoutRequest.getPassword())) throw new InvalidDetailsException();


     user.setLogin(false);
     userRepository.save(user);
    }

    private boolean userExist(String username){
        User user = userRepository.findUserByUserName(username);
        return  user != null;
    }
}
