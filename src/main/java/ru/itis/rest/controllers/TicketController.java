package ru.itis.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.rest.dto.TicketDto;
import ru.itis.rest.dto.UserDto;
import ru.itis.rest.services.TicketService;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/tickets")
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto ticketDto, @RequestBody UserDto userDto) {
        ticketService.createTicket(ticketDto, userDto);
        return ResponseEntity.ok().build();
    }
}
