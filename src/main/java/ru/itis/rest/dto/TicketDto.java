package ru.itis.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.rest.models.Ticket;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto {

    private Long id;
    private String message;

    public static TicketDto from(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .message(ticket.getTicketMessage())
                .build();
    }

    public static List<TicketDto> from(List<Ticket> tickets) {
        return tickets.stream().map(TicketDto::from).collect(Collectors.toList());
    }
}
