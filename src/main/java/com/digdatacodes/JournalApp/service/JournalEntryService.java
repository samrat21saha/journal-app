package com.digdatacodes.JournalApp.service;

import com.digdatacodes.JournalApp.entity.JournalEntry;
import com.digdatacodes.JournalApp.entity.User;
import com.digdatacodes.JournalApp.repository.JournalEntryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    private static final Logger log = LoggerFactory.getLogger(JournalEntryService.class);

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String userEmail) {
        try {
            User user = userService.findByEmail(userEmail);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            log.error("Failed to save journal entry for user {}", userEmail, e);
            throw new RuntimeException("An error occurred while saving the entry", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findById(String id) {
        return journalEntryRepo.findById(id);
    }

    public boolean deleteById(String id, String userEmail) {
        boolean removed = false;
        try {
            User user = userService.findByEmail(userEmail);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepo.deleteById(id);
            }
        } catch (Exception e) {
            log.error("Failed to delete journal entry {} for user {}", id, userEmail, e);
            throw new RuntimeException("Error occurred while deleting the entry", e);
        }
        return removed;
    }

    public void deleteAllEntries(List<JournalEntry> entries) {
        if (entries != null && !entries.isEmpty()) {
            journalEntryRepo.deleteAll(entries);
        }
    }
}