package ru.itis.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.rest.models.Ticket;

import java.util.List;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findTicketsByAccount_Id(Long userId);
}
