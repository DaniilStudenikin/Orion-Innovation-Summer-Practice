package ru.itis.rest.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.rest.dto.UserDto;
import ru.itis.rest.services.AdminService;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
public class AdminUsersController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUsersController.class);

    @Autowired
    private AdminService adminService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserByEmail(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(adminService.findByEmail(userDto.getEmail()));
    }

    //    @ApiOperation(value = "Получение всех пользователей")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Пользователи успешно получены", response = UserDto.class)})
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    //    @ApiOperation(value = "Добавление пользователя")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Пользователь успешно добавлен в бд", response = UserDto.class)})
    @PermitAll
    @PostMapping("/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) {
        try {
            adminService.addUser(user);
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
        }
        return ResponseEntity.ok(adminService.findByEmail(user.getEmail()));
    }

    //    @ApiOperation(value = "Обновленние данных пользователя")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Данные пользователя обновлены", response = UserDto.class)})
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/users/{user_id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("user_id") Long userId, @RequestBody UserDto user) {
        try {
            adminService.updateUser(userId, user);
            return ResponseEntity.ok(adminService.findByEmail(user.getEmail()));
        } catch (IllegalArgumentException e) {
            logger.info("Update problems: id is not found", e);
            return ResponseEntity.notFound().build();
        }

    }

    //    @ApiOperation(value = "Удаление пользователя")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Пользователь успешно удален", response = UserDto.class)))
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") Long userId) {
        try {
            adminService.deleteUser(userId);
            return ResponseEntity.ok("User with id: " + userId + " is deleted");

        } catch (IllegalArgumentException e) {
            logger.info("Update problems: id is not found", e);
            return ResponseEntity.ok("User with id: " + userId + " does not exists ");
        }
    }

    //    @ApiOperation(value = "Забанить пользователя")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Пользователь забанен", response = UserDto.class)})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/users/ban/{user_id}")
    public ResponseEntity<UserDto> banUser(@PathVariable("user_id") Long userId, @RequestBody UserDto user) {
        try {
            adminService.banUser(userId);
            return ResponseEntity.ok(adminService.findByEmail(user.getEmail()));
        } catch (IllegalArgumentException e) {
            logger.info("Banning problems: id is not found", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
