package ru.itis.rest.controllers;

//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.itis.rest.dto.UserDto;
import ru.itis.rest.services.AdminService;
import ru.itis.rest.services.UsersHistoryService;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AdminUsersController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUsersController.class);

    @Autowired
    private AdminService adminService;
    @Autowired
    private UsersHistoryService usersHistoryService;

    @PermitAll
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserByEmail(@RequestBody UserDto userDto, HttpServletRequest request) {
        try {
            usersHistoryService.updateNote(request.getRemoteUser(), String.valueOf(request.getRequestURL()));
        } catch (IllegalArgumentException e) {
            logger.info(String.valueOf(e));
            usersHistoryService.addNote(adminService.findByEmail(request.getRemoteUser()).getId(), String.valueOf(request.getRequestURL()));
            return ResponseEntity.ok(adminService.findByEmail(userDto.getEmail()));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adminService.findByEmail(userDto.getEmail()));
    }

//    @ApiOperation(value = "Получение всех пользователей")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Пользователи успешно получены", response = UserDto.class)})
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getUsers(HttpServletRequest request) {
        try {
            usersHistoryService.updateNote(request.getRemoteUser(), String.valueOf(request.getRequestURL()));
        } catch (UsernameNotFoundException|IllegalArgumentException e) {
            usersHistoryService.addNote(adminService.findByEmail(request.getRemoteUser()).getId(), String.valueOf(request.getRequestURL()));
            return ResponseEntity.ok(adminService.getAllUsers());
        }
        return ResponseEntity.ok(adminService.getAllUsers());
    }

//    @ApiOperation(value = "Добавление пользователя")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Пользователь успешно добавлен в бд", response = UserDto.class)})
    @PermitAll
    @PostMapping("/addUser")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user, HttpServletRequest request) {
        try {
            adminService.addUser(user);
            usersHistoryService.addNote(adminService.findByEmail(user.getEmail()).getId(), String.valueOf(request.getRequestURL()));
        } catch (IllegalArgumentException | UsernameNotFoundException e) {
            logger.info(e.getMessage());
        }
        return ResponseEntity.ok(adminService.findByEmail(user.getEmail()));
    }

//    @ApiOperation(value = "Обновленние данных пользователя")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Данные пользователя обновлены", response = UserDto.class)})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateUser/{user_id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("user_id") Long userId, @RequestBody UserDto user, HttpServletRequest request) {
        try {
            adminService.updateUser(userId, user);
            usersHistoryService.updateNote(userId);
            return ResponseEntity.ok(adminService.findByEmail(user.getEmail()));
        } catch (IllegalArgumentException e) {
            logger.info(String.valueOf(e));
            usersHistoryService.addNote(userId, String.valueOf(request.getRequestURL()));
            return ResponseEntity.ok(adminService.findByEmail(user.getEmail()));
        } catch (UsernameNotFoundException e) {
            logger.info("Update problems: id is not found", e);
            usersHistoryService.addNote(userId, String.valueOf(request.getRequestURL()));
            return ResponseEntity.notFound().build();
        }
    }
//    @ApiOperation(value = "Удаление пользователя")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Пользователь успешно удален", response = UserDto.class)})
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") Long userId) {
        try {
            adminService.deleteUser(userId);
            usersHistoryService.deleteNote(userId);
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
    public ResponseEntity<UserDto> banUser(@PathVariable("user_id") Long userId, @RequestBody UserDto user, HttpServletRequest request) {
        try {
            adminService.banUser(userId);
            usersHistoryService.updateNote(request.getRemoteUser(), String.valueOf(request.getRequestURL()));
            return ResponseEntity.ok(adminService.findByEmail(user.getEmail()));
        } catch (IllegalArgumentException e) {
            logger.info("Banning problems: id is not found", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
