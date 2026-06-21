package com.digdatacodes.JournalApp.controller;

import com.digdatacodes.JournalApp.entity.JournalEntry;
import com.digdatacodes.JournalApp.entity.User;
import com.digdatacodes.JournalApp.service.JournalEntryService;
import com.digdatacodes.JournalApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private static final Logger log = LoggerFactory.getLogger(JournalEntryController.class);

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if (allEntries == null) {
            allEntries = Collections.emptyList();
        }
        return new ResponseEntity<>(allEntries, HttpStatus.OK);
    }

    @GetMapping("/id/{jid}")
    public ResponseEntity<?> getEntryById(@PathVariable String jid) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = userService.findByEmail(userEmail);
            boolean owns = user.getJournalEntries().stream().anyMatch(x -> x.getId().equals(jid));
            if (owns) {
                Optional<JournalEntry> journalEntry = journalEntryService.findById(jid);
                if (journalEntry.isPresent()) {
                    return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Failed to fetch journal entry {}", jid, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            journalEntryService.saveEntry(myEntry, userEmail);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Failed to create journal entry", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{jid}")
    public ResponseEntity<?> deleteEntryById(@PathVariable String jid) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            boolean removed = journalEntryService.deleteById(jid, userEmail);
            if (removed) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Failed to delete journal entry {}", jid, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/id/{jid}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable String jid,
            @RequestBody JournalEntry newEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = userService.findByEmail(userEmail);
            boolean owns = user.getJournalEntries().stream().anyMatch(x -> x.getId().equals(jid));
            if (owns) {
                Optional<JournalEntry> journalEntry = journalEntryService.findById(jid);
                if (journalEntry.isPresent()) {
                    JournalEntry old = journalEntry.get();
                    old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isBlank() ? newEntry.getTitle() : old.getTitle());
                    old.setContent(newEntry.getContent() != null && !newEntry.getContent().isBlank() ? newEntry.getContent() : old.getContent());
                    old.setCategory(newEntry.getCategory() != null ? newEntry.getCategory() : old.getCategory());
                    journalEntryService.saveEntry(old);
                    return new ResponseEntity<>(old, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Failed to update journal entry {}", jid, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}