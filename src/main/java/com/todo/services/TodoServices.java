package com.todo.services;

import com.todo.data.models.Task;
import com.todo.data.models.User;
import com.todo.dtos.request.*;

import java.util.List;

public interface TodoServices {
    User register(RegisterRequest registerRequest);
    void login(LoginRequest loginRequest);
    User findAccountBelongingTo(String username);
    void logout(LogoutRequest logoutRequest);
    Task addTask(TaskRequest taskRequest);
    void  completeTask(CompleteTaskRequest completeTaskRequest);
    Task findTaskById(String taskId);

    void deleteTask(DeleteTaskRequest deleteTaskRequest);
    Task findTaskBy(FindTaskRequest findTaskRequest);
    List<Task> findAllTask(String userId);

    Task editTaskDueDate(EditTaskDateRequest editTaskDateRequest);
    Task editTaskMessage(EditTaskMessageRequest editTaskMessageRequest);
    void deleteAllTask(String userId);
    void deleteAccount(DeleteAccountRequest deleteAccountRequest);
}
