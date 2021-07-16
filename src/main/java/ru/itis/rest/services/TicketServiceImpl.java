package ru.itis.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.rest.dto.TicketDto;
import ru.itis.rest.dto.UserDto;
import ru.itis.rest.models.Ticket;
import ru.itis.rest.models.User;
import ru.itis.rest.repositories.TicketsRepository;
import ru.itis.rest.repositories.UsersRepository;

import java.util.List;

import static ru.itis.rest.dto.TicketDto.*;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TicketsRepository ticketsRepository;

    @Override
    public List<TicketDto> getAllTickets(Long accountId) {
        List<Ticket> tickets = ticketsRepository.findTicketsByAccount_Id(accountId);
        return from(tickets);
    }

    @Override
    public void createTicket(TicketDto ticketDto, UserDto userDto) {
        User userWhoCreatingTicket = usersRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not fount"));
        Ticket ticketToCreate = Ticket.builder()
                .ticketMessage(ticketDto.getMessage())
                .account(userWhoCreatingTicket)
                .status(Ticket.Status.ACTIVE)
                .build();
        ticketsRepository.save(ticketToCreate);
    }

    @Override
    public void closeTicket(TicketDto ticketDto) {

    }
}
