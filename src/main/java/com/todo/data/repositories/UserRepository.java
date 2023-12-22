package com.todo.data.repositories;

import com.todo.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findUserByUserName(String username);
   User findUserByUserId(String userId);

}
