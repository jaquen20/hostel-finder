package com.example.best.exception;

public class HostelsNotFoundException extends RuntimeException{
    public HostelsNotFoundException(String id) {
        super("User not found with username: " + id);
    }
}
