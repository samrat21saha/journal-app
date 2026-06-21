package com.digdatacodes.JournalApp.repository;

import com.digdatacodes.JournalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {

    User findByEmail(String email);

    void deleteByEmail(String email);
}