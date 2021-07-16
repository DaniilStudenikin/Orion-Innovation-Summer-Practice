package ru.itis.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.rest.models.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);
}
