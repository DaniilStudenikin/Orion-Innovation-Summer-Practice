package ru.itis.rest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UsersHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "lastUpdatedTime")
    private LocalDateTime lastUpdatedTime;

    @Column(name = "lastRequest")
    private String lastRequest;

    @Column(name = "isDeleted")
    private Boolean isDeleted;
}
