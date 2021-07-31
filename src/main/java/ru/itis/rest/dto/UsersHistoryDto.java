package ru.itis.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.rest.models.UsersHistory;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersHistoryDto {
    private Long id;
    private String lastRequest;

    public static UsersHistoryDto from(UsersHistory usersHistory) {
        return UsersHistoryDto.builder()
                .id(usersHistory.getId())
                .lastRequest(usersHistory.getLastRequest())
                .build();
    }

    public static List<UsersHistoryDto> from(List<UsersHistory> usersHistories) {
        return usersHistories.stream().map(UsersHistoryDto::from).collect(Collectors.toList());
    }
}
