package com.todo.controllers;

import com.todo.data.models.Task;
import com.todo.data.models.User;
import com.todo.dtos.request.*;
import com.todo.dtos.response.*;
import com.todo.services.TodoServices;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
   private TodoServices todoServices;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = new RegisterResponse();
        try {
           User user = todoServices.register(registerRequest);
            registerResponse.setMessage("Registration completed, User ID is " + user.getUserId());
            return new ResponseEntity<>(new ApiResponse(true,registerResponse) , HttpStatus.CREATED);
        }catch (Exception exception){
            registerResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,registerResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse();
        try {
            todoServices.login(loginRequest);
            loginResponse.setMessage("you are logged in");
            return new ResponseEntity<>(new ApiResponse(true,loginResponse),HttpStatus.ACCEPTED);
        }catch (Exception e){
            loginResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,loginResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest){
        LogoutResponse logoutResponse = new LogoutResponse();

        try {
            todoServices.logout(logoutRequest);
            logoutResponse.setMessage("you are logged out");
            return new ResponseEntity<>(new ApiResponse(true,logoutResponse),HttpStatus.ACCEPTED);
        }catch (Exception exception){
            logoutResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,logoutResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody TaskRequest taskRequest){
        AddTaskResponse addTaskResponse = new AddTaskResponse();
        try {
            Task task = todoServices.addTask(taskRequest);
            addTaskResponse.setMessage("Task has been added successfully, Task id is " + task);
            return new ResponseEntity<>(new ApiResponse(true,addTaskResponse),HttpStatus.ACCEPTED);
        }catch (Exception e){
            addTaskResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,addTaskResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/completeTask")
    public ResponseEntity<?> completeTask(@RequestBody CompleteTaskRequest completeTaskRequest){
        CompleteTaskResponse completeTaskResponse = new CompleteTaskResponse();

        try {
            todoServices.completeTask(completeTaskRequest);
            completeTaskResponse.setMessage("Weldone!!! task has been completed");
            return new ResponseEntity<>(new ApiResponse(true,completeTaskResponse),HttpStatus.ACCEPTED);
        }
        catch (Exception exception){
            completeTaskResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,completeTaskRequest),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<?> deleteTask(@RequestBody DeleteTaskRequest deleteTaskRequest){
        DeleteTaskResponse deleteTaskResponse = new DeleteTaskResponse();
        try {
            todoServices.deleteTask(deleteTaskRequest);
            deleteTaskResponse.setMessage("Task has been deleted");
            return new ResponseEntity<>(new ApiResponse(true,deleteTaskResponse),HttpStatus.ACCEPTED);
        }catch (Exception exception){
            deleteTaskResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,deleteTaskResponse),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findTask")
    public ResponseEntity<?> findTaskBy(@RequestBody FindTaskRequest findTaskRequest){
     FindTaskResponse findTaskResponse = new FindTaskResponse();
     try {
        Task task = todoServices.findTaskBy(findTaskRequest);
         findTaskResponse.setMessage(String.valueOf(task));
         return new ResponseEntity<>(new ApiResponse(true,findTaskResponse),HttpStatus.ACCEPTED);
     }catch (Exception exception){
         findTaskResponse.setMessage(exception.getMessage());
         return new ResponseEntity<>(new ApiResponse(false,findTaskResponse),HttpStatus.BAD_REQUEST);
     }
    }

    @GetMapping("/findAll/{userId}")
    public ResponseEntity<?> findAllTask(@PathVariable("userId") String userId){
     FindAllTaskResponse findAllTaskResponse = new FindAllTaskResponse();

     try {
         List<Task> taskList = todoServices.findAllTask(userId);
         findAllTaskResponse.setMessage(String.valueOf(taskList));
         return new ResponseEntity<>(new ApiResponse(true,findAllTaskResponse),HttpStatus.ACCEPTED);
     }catch (Exception exception){
         findAllTaskResponse.setMessage(exception.getMessage());
         return new ResponseEntity<>(new ApiResponse(false,findAllTaskResponse),HttpStatus.BAD_REQUEST);
     }
    }

    @PatchMapping("/editDate")
    public ResponseEntity<?> editDueDate(@RequestBody EditTaskDateRequest editTaskDateRequest){
      EditResponse editResponse = new EditResponse();

      try {
          Task task = todoServices.editTaskDueDate(editTaskDateRequest);
          editResponse.setMessage("Due date edited successfully \n" + task);
          return new ResponseEntity<>(new ApiResponse(true,editResponse),HttpStatus.ACCEPTED);
      }catch (Exception exception){
          editResponse.setMessage(editResponse.getMessage());
          return new ResponseEntity<>(new ApiResponse(false,editResponse),HttpStatus.BAD_REQUEST);
      }
    }

    @PatchMapping("/editMessage")
    public ResponseEntity<?> editMessage(@RequestBody EditTaskMessageRequest editTaskMessageRequest){
        EditResponse editResponse = new EditResponse();

        try {
            Task task = todoServices.editTaskMessage(editTaskMessageRequest);
            editResponse.setMessage("Task edited successfully \n" + task);
            return new ResponseEntity<>(new ApiResponse(true,editResponse),HttpStatus.ACCEPTED);
        }catch (Exception exception){
            editResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,editResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAll/{userId}")
    public ResponseEntity<?> deleteAllTask(@PathVariable("userId") String userId){
    DeleteAllTaskResponse deleteAllTaskResponse = new DeleteAllTaskResponse();

    try {
        todoServices.deleteAllTask(userId);
        deleteAllTaskResponse.setMessage("All task has been deleted");
        return new ResponseEntity<>(new ApiResponse(true,deleteAllTaskResponse),HttpStatus.ACCEPTED);
    }catch (Exception exception){
        deleteAllTaskResponse.setMessage(exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(false,deleteAllTaskResponse),HttpStatus.BAD_REQUEST);
    }
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<?> deleteAccount(@RequestBody DeleteAccountRequest deleteAccountRequest){
        DeleteAccountResponse deleteAccountResponse = new DeleteAccountResponse();

        try {
            todoServices.deleteAccount(deleteAccountRequest);
            deleteAccountResponse.setMessage("Account deleted successfully");
            return new ResponseEntity<>(new ApiResponse(true,deleteAccountResponse),HttpStatus.ACCEPTED);
        }catch (Exception exception){
            deleteAccountResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,deleteAccountRequest),HttpStatus.BAD_REQUEST);
        }
    }


}
