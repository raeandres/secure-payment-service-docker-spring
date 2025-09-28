package com.raeandres.secure_payment_service_docker_spring.service.cqrs;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {

    @Test
    void testQueryImplementation() {
        Query<String, ArrayList<String>> query = new Query<String, ArrayList<String>>() {
            @Override
            public ResponseEntity<ArrayList<String>> execute(String input) {
                ArrayList<String> result = new ArrayList<>();
                result.add("Query result for: " + input);
                return ResponseEntity.ok(result);
            }
        };

        ResponseEntity<ArrayList<String>> result = query.execute("test");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("Query result for: test", result.getBody().get(0));
    }

    @Test
    void testQueryWithNullInput() {
        Query<String, ArrayList<String>> query = new Query<String, ArrayList<String>>() {
            @Override
            public ResponseEntity<ArrayList<String>> execute(String input) {
                ArrayList<String> result = new ArrayList<>();
                if (input == null) {
                    result.add("No input provided");
                }
                return ResponseEntity.ok(result);
            }
        };

        ResponseEntity<ArrayList<String>> result = query.execute(null);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("No input provided", result.getBody().get(0));
    }
}
