package ru.itis.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.rest.models.UsersHistory;

import java.util.Optional;

public interface UsersHistoryRepository extends JpaRepository<UsersHistory, Long> {
    void deleteUsersHistoryByUserId(Long id);

    Optional<UsersHistory> findUsersHistoriesByUserId(Long id);
}
