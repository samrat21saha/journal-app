package com.digdatacodes.JournalApp.service;

import com.digdatacodes.JournalApp.entity.User;
import com.digdatacodes.JournalApp.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        try {
            userRepo.save(user);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("Email '" + user.getEmail() + "' is already registered");
        }
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        try {
            userRepo.save(user);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("Email '" + user.getEmail() + "' is already registered");
        }
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(User existingUser, User incoming) {
        if (incoming.getEmail() != null && !incoming.getEmail().isBlank()) {
            existingUser.setEmail(incoming.getEmail());
        }
        if (incoming.getPassword() != null && !incoming.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(incoming.getPassword()));
        }
        userRepo.save(existingUser);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public Optional<User> findById(String id) {
        return userRepo.findById(id);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void deleteById(String id) {
        userRepo.deleteById(id);
    }

    public void initiatePasswordReset(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            log.info("Password reset requested for an email with no matching account: {}", email);
            return; // don't reveal whether the account exists
        }
        String otp = generateOtp();
        user.setOtpCode(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
        userRepo.save(user);
        emailService.sendPasswordResetOtp(user.getEmail(), otp);
        log.info("Password reset OTP sent to {}", email);
    }

    public void completePasswordReset(String email, String otp, String newPassword) {
        User user = userRepo.findByEmail(email);
        if (user == null || user.getOtpCode() == null || user.getOtpExpiry() == null
                || !Objects.equals(user.getOtpCode(), otp)
                || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid or expired code");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtpCode(null);
        user.setOtpExpiry(null);
        userRepo.save(user);
    }

    private String generateOtp() {
        int code = 100000 + RANDOM.nextInt(900000); // always 6 digits
        return String.valueOf(code);
    }
}