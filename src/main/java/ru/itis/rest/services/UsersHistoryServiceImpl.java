package ru.itis.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.rest.models.User;
import ru.itis.rest.models.UsersHistory;
import ru.itis.rest.repositories.UsersHistoryRepository;
import ru.itis.rest.repositories.UsersRepository;

import java.time.LocalDateTime;

@Service
public class UsersHistoryServiceImpl implements UsersHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(UsersHistoryServiceImpl.class);

    @Autowired
    private UsersHistoryRepository historyRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void addNote(Long id, String URL) {
        User user = usersRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        LocalDateTime time = LocalDateTime.now();
        UsersHistory usersHistory = UsersHistory.builder()
                .user(user)
                .createdTime(time)
                .lastUpdatedTime(time)
                .lastRequest(URL)
                .build();
        historyRepository.save(usersHistory);
    }

    @Override
    public void updateNote(Long id) {
        User user = usersRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UsersHistory noteForUpdate = historyRepository.findUsersHistoriesByUserId(user.getId()).orElseThrow(IllegalArgumentException::new);
        noteForUpdate.setLastUpdatedTime(LocalDateTime.now());
        historyRepository.save(noteForUpdate);
    }

    @Override
    public void deleteNote(Long id) {
        UsersHistory usersHistory = historyRepository.findUsersHistoriesByUserId(id).orElseThrow(IllegalArgumentException::new);
        usersHistory.setIsDeleted(true);
        usersHistory.setLastUpdatedTime(LocalDateTime.now());
        historyRepository.save(usersHistory);

    }

    @Override
    public void updateNote(String email, String URL) {
        User user = usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        logger.info(String.valueOf(user));
        logger.info(String.valueOf(historyRepository.findUsersHistoriesByUserId(user.getId()).orElseThrow(IllegalArgumentException::new)));
        UsersHistory noteForUpdate = historyRepository.findUsersHistoriesByUserId(user.getId()).orElseThrow(IllegalArgumentException::new);
        noteForUpdate.setLastRequest(URL);
        noteForUpdate.setLastUpdatedTime(LocalDateTime.now());
        historyRepository.save(noteForUpdate);
    }
}
