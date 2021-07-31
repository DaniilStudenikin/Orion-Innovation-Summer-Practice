package ru.itis.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itis.rest.models.UsersHistory;
import ru.itis.rest.repositories.UsersHistoryRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class HistoryChecker {

    @Autowired
    private UsersHistoryRepository usersHistoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(HistoryChecker.class);

    @Scheduled(cron = "0 0 9-17 * * MON-FRI")
    public void checker() {
        List<UsersHistory> histories = usersHistoryRepository.findAll();
        for (UsersHistory history : histories) {
            if (Duration.between(history.getLastUpdatedTime(), LocalDateTime.now()).toMinutes() > 100) {
                usersHistoryRepository.findById(history.getId()).orElseThrow(IllegalArgumentException::new).setIsDeleted(true);
                logger.info(history + " note deleted at" + LocalDateTime.now());
            }
        }
    }
}
