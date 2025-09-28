package com.raeandres.secure_payment_service_docker_spring.service.cqrs;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void testCommandImplementation() {
        Command<String, String> command = new Command<String, String>() {
            @Override
            public ResponseEntity<String> execute(String input) {
                return ResponseEntity.ok("Executed: " + input);
            }
        };

        ResponseEntity<String> result = command.execute("test");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Executed: test", result.getBody());
    }

    @Test
    void testCommandWithNullInput() {
        Command<String, String> command = new Command<String, String>() {
            @Override
            public ResponseEntity<String> execute(String input) {
                return ResponseEntity.ok("Executed: " + input);
            }
        };

        ResponseEntity<String> result = command.execute(null);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Executed: null", result.getBody());
    }
}
