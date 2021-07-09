package ru.itis.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.rest.dto.UserDto;
import ru.itis.rest.services.UsersService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UsersController {

    Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/site")
    public ResponseEntity<List<UserDto>> getTeacher() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @PostMapping("/site")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) {
        logger.info(String.valueOf(user));
        usersService.addUser(user);
        return ResponseEntity.ok(usersService.findByEmail(user.getEmail()));
    }

    @PutMapping("/site/{user_id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("user_id") Long userId, @RequestBody UserDto user) {
        try {
            usersService.updateUser(userId, user);
            return ResponseEntity.ok(usersService.findByEmail(user.getEmail()));
        } catch (IllegalArgumentException e) {
            logger.info("Update problems: id is not found", e);
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/site/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") Long userId) {
        try {
            usersService.deleteUser(userId);
            return ResponseEntity.ok("User with id: " + userId + " is deleted");

        } catch (IllegalArgumentException e) {
            logger.info("Update problems: id is not found", e);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/site/ban/{user_id}")
    public ResponseEntity<UserDto> banUser(@PathVariable("user_id") Long userId, @RequestBody UserDto user) {
        try {
            usersService.banUser(userId);
            return ResponseEntity.ok(usersService.findByEmail(user.getEmail()));
        } catch (IllegalArgumentException e) {
            logger.info("Banning problems: id is not found", e);
            return ResponseEntity.status(404).build();
        }
    }
}
