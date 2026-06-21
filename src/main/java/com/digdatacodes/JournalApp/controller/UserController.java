package com.digdatacodes.JournalApp.controller;

import com.digdatacodes.JournalApp.entity.User;
import com.digdatacodes.JournalApp.repository.UserRepo;
import com.digdatacodes.JournalApp.service.JournalEntryService;
import com.digdatacodes.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JournalEntryService journalEntryService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User userInDb = userService.findByEmail(userEmail);
        if (userInDb == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.updateUser(userInDb, user); // preserves existing roles, ignores blank fields
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User userInDb = userService.findByEmail(userEmail);
        if (userInDb != null) {
            journalEntryService.deleteAllEntries(userInDb.getJournalEntries()); // no orphaned entries
            userRepo.deleteByEmail(userEmail);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}