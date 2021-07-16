package ru.itis.rest.services;

import ru.itis.rest.dto.TicketDto;
import ru.itis.rest.dto.UserDto;
import ru.itis.rest.models.Ticket;

import java.util.List;

public interface TicketService {
    List<TicketDto> getAllTickets(Long accountId);

    void createTicket(TicketDto ticketDto, UserDto userDto);

    void closeTicket(TicketDto ticketDto);
}
