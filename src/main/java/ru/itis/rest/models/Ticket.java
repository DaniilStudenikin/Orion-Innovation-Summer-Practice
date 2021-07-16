package ru.itis.rest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketMessage;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private User account;

    private boolean isDeleted;

    public enum Status {
        ACTIVE, CLOSED
    }

}
