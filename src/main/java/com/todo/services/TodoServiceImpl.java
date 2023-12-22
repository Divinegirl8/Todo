package com.todo.services;

import com.todo.data.models.Task;
import com.todo.data.models.User;
import com.todo.data.repositories.TaskRepository;
import com.todo.data.repositories.UserRepository;
import com.todo.dtos.request.*;
import com.todo.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.todo.utils.Mapper.*;

@Service
public class TodoServiceImpl implements TodoServices{
    @Autowired
   private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
   private UserRepository userRepository;


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
        if (user.getPassword() == null || !user.getPassword().equals(loginRequest.getPassword())) {
            throw new InvalidDetailsException();
        }

        user.setLogin(true);
        userRepository.save(user);

    }



    @Override
    public User findAccountBelongingTo(String username) {
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

    @Override
    public Task addTask(TaskRequest taskRequest) {
        User user =  userRepository.findUserByUserId(taskRequest.getUserId());

        if (user == null) throw new NoUserExist(taskRequest.getUserId() + " not found");
        if (!user.isLogin()) throw new NotLoginException("you must login before performing this action");

        int taskId = user.getTaskId()+1;
        user.setTaskId(taskId);
        userRepository.save(user);
      Task task =  taskService.setTask("TID" +(user.getTaskId()),taskRequest.getUserId(), LocalDateTime.now(),taskRequest.getMessage(),taskRequest.getDueDate(),false);

      taskRepository.save(task);
      return task;

    }

    @Override
    public void completeTask(CompleteTaskRequest completeTaskRequest) {
      User user = userRepository.findUserByUserId(completeTaskRequest.getUserId());
      if (user == null) throw new NoUserExist(completeTaskRequest.getUserId() + " does not exist");
      if (!user.isLogin()) throw new NotLoginException("you must login before performing this action");

      Task task = findTaskById(completeTaskRequest.getTaskId());

      task.setUserId(user.getUserId());
      task.setStatus(true);
      taskRepository.save(task);

    }

    @Override
    public Task findTaskById(String taskId) {
        Task task = taskRepository.findByTaskId(taskId);
        if (task == null) throw new TaskIdNotFoundException(taskId + " not found");
        return task;
    }

    @Override
    public void deleteTask(DeleteTaskRequest deleteTaskRequest) {
      User user = userRepository.findUserByUserId(deleteTaskRequest.getUserId());
        if (user == null) throw new NoUserExist(deleteTaskRequest.getUserId() + " does not exist");
        if (!user.isLogin()) throw new NotLoginException("you must login before performing this action");
        if (!user.getPassword().equals(deleteTaskRequest.getPassword())) throw new InvalidDetailsException();


        Task task = taskRepository.findByTaskId(deleteTaskRequest.getTaskId());

        if (task == null) throw new TaskIdNotFoundException(deleteTaskRequest.getTaskId() + " not found");
        if (!task.getUserId().equals(user.getUserId())) {
            throw new UnauthorizedTaskAccessException("You are not authorized to delete this task");
        }
        taskRepository.delete(task);

    }

    @Override
    public Task findTaskBy(String userId, String taskId) {
        User user = userRepository.findUserByUserId(userId);

        if ( user ==  null) throw new NoUserExist(userId + " does not exist");
        if (!user.isLogin()) throw new NotLoginException("you must login before performing this action");

        Task task = taskRepository.findByTaskId(taskId);

        if (task == null) throw new TaskIdNotFoundException(taskId + " not found");
        task.setUserId(user.getUserId());
        taskRepository.save(task);

        return task;
    }

    @Override
    public List<Task> findAllTask(String userId) {
        User user = userRepository.findUserByUserId(userId);
        List<Task> taskList = new ArrayList<>();

        if ( user ==  null) throw new NoUserExist(userId + " does not exist");
        for (Task task : taskService.findAll()){
            if (task.getUserId().equals(user.getUserId())) taskList.add(task);
        }
     return taskList;
    }

    @Override
    public Task editTaskDueDate(EditTaskDateRequest editTaskDateRequest) {
        User user = userRepository.findUserByUserId(editTaskDateRequest.getUserId());

        if ( user ==  null) throw new NoUserExist(editTaskDateRequest.getUserId() + " does not exist");
        if (!user.isLogin()) throw new NotLoginException("you must login before performing this action");

        Task task = taskRepository.findByTaskId(editTaskDateRequest.getTaskId());

        if (task == null) throw new TaskIdNotFoundException(editTaskDateRequest.getTaskId() + " not found");
        task.setDueDate(editTaskDateRequest.getDueDate());
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task editTaskMessage(EditTaskMessageRequest editTaskMessageRequest) {
        User user = userRepository.findUserByUserId(editTaskMessageRequest.getUserId());

        if ( user ==  null) throw new NoUserExist(editTaskMessageRequest.getUserId() + " does not exist");
        if (!user.isLogin()) throw new NotLoginException("you must login before performing this action");

        Task task = taskRepository.findByTaskId(editTaskMessageRequest.getTaskId());

        if (task == null) throw new TaskIdNotFoundException(editTaskMessageRequest.getTaskId() + " not found");

        if (editTaskMessageRequest.isAppendToMessage()){
            String newMessage = task.getMessage() + " " + editTaskMessageRequest.getMessage();
            task.setMessage(newMessage);
        } else {
            task.setMessage(editTaskMessageRequest.getMessage());
        }
        taskRepository.save(task);
        return task;
    }

    @Override
    public void deleteAllTask(String userId) {
        User user = userRepository.findUserByUserId(userId);

        if ( user ==  null) throw new NoUserExist(userId + " does not exist");
        if (!user.isLogin()) throw new NotLoginException("you must login before performing this action");

        List<Task> task = taskRepository.findByUserId(userId);
        taskRepository.deleteAll(task);
    }

    @Override
    public void deleteAccount(DeleteAccountRequest deleteAccountRequest) {
        User user = userRepository.findUserByUserName(deleteAccountRequest.getUsername());

        if (!userExist(deleteAccountRequest.getUsername())) throw new InvalidDetailsException();
        if (user.getPassword() == null || !user.getPassword().equals(deleteAccountRequest.getPassword())) {
            throw new InvalidDetailsException();
        }
        userRepository.delete(user);
    }


    private boolean userExist(String username){
        User user = userRepository.findUserByUserName(username);
        return  user != null;
    }


}
