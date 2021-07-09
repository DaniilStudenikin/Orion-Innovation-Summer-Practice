package ru.itis.rest.services;

import ru.itis.rest.dto.UserDto;

import java.util.List;

public interface UsersService {
    List<UserDto> getAllUsers();

    void addUser(UserDto user);

    void updateUser(Long userId, UserDto user);

    void deleteUser(Long userId);

    UserDto findByEmail(String email);

    void banUser(Long userId);
}
