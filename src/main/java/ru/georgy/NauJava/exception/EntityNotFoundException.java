package ru.georgy.NauJava.exception;

public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("%s с id %d не найден", entityName, id));
    }
    
    public EntityNotFoundException(String message) {
        super(message);
    }
} 