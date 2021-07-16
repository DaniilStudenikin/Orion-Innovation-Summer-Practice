package ru.itis.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.rest.dto.UserDto;
import ru.itis.rest.models.User;
import ru.itis.rest.repositories.UsersRepository;

import java.util.List;

import static ru.itis.rest.dto.UserDto.from;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = usersRepository.findAll();
        return from(users);
    }

    @Override
    public void addUser(UserDto user) {
        User newUser = User.builder()
                .email(user.getEmail())
                .hashPassword(passwordEncoder.encode(user.getPassword()))
                .role(User.Role.USER)
                .state(User.State.ACTIVE)
                .build();
        usersRepository.save(newUser);
    }

    @Override
    public void updateUser(Long userId, UserDto user) {
        User userForUpdate = usersRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        userForUpdate.setEmail(user.getEmail());
        userForUpdate.setHashPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(userForUpdate);
    }

    @Override
    public void deleteUser(Long userId) {
        User userForDelete = usersRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        userForDelete.setIsDeleted(true);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = usersRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
        return from(user);
    }

    @Override
    public void banUser(Long userId) {
        List<User> users = usersRepository.findAll();
        for (User user : users) {
            if (!user.isAdmin()) {
                user.setState(User.State.BANNED);
                usersRepository.save(user);
            }
        }
    }
}
