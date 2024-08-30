package com.day4.logging_monitoring;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/entities")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        Long startTime = System.currentTimeMillis();
        List<User> allUsers = userService.getAllUsers();
        Long endTime = System.currentTimeMillis();

        if (allUsers == null) {
            log.warn("List of Users is empty. Please insert new User.");
        }

        log.info("Waktu yang dibutuhkan untuk memproses request: {}", endTime-startTime);
        return allUsers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Long startTime = System.currentTimeMillis();
        User searchedUser = userService.getUserById(id);

        if (searchedUser == null) {
            Long endTime = System.currentTimeMillis();
            log.error("User with id {} not found", id);
            log.info("Waktu yang dibutuhkan untuk memproses request: {}", endTime-startTime);
            return ResponseEntity.notFound().build();
        }
        Long endTime = System.currentTimeMillis();
        log.info("Waktu yang dibutuhkan untuk memproses request: {}", endTime-startTime);
        return ResponseEntity.ok().body(searchedUser);
    }

    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody User User) {
        Long startTime = System.currentTimeMillis();
        try {
            userService.userRepository.save(User);
            Long endTime = System.currentTimeMillis();
            log.info("Waktu yang dibutuhkan untuk memproses request: {}", endTime-startTime);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            Long endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            log.info("Waktu yang dibutuhkan untuk memproses request: {}", endTime-startTime);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable Long id, @Valid @RequestBody User User) {
        Long startTime = System.currentTimeMillis();
        try {
            userService.updateUser(id, User);
            Long endTime = System.currentTimeMillis();
            log.info("Waktu yang dibutuhkan untuk memproses request: {}", endTime-startTime);
            return ResponseEntity.ok() .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            Long endTime = System.currentTimeMillis();
            log.info("Waktu yang dibutuhkan untuk memproses request: {}", endTime-startTime);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Long startTime = System.currentTimeMillis();
        try {
            userService.userRepository.deleteById(id);
            Long endTime = System.currentTimeMillis();
            log.info("Waktu yang dibutuhkan untuk memproses request: {}", endTime-startTime);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            Long endTime = System.currentTimeMillis();
            log.info("Waktu yang dibutuhkan untuk memproses request: {}", endTime-startTime);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
