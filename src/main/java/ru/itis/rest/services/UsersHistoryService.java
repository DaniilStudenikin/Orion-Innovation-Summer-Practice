package ru.itis.rest.services;


public interface UsersHistoryService {
    void addNote(Long id, String URL);

    void updateNote(Long id);

    void deleteNote(Long id);

    void updateNote(String email, String URL);
}
