package com.manning.javapersistence.springdatajpa;


import com.manning.javapersistence.springdatajpa.model.User;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class QueryResultsTest extends SpringdatajpaApplicationTests{

    @Test
    void testStreamable() {
        try (Stream<User> result = userRepository.findByEmailContaining("someother")
                .and(userRepository.findByLevel(2))
                .stream().distinct()) {
            assertEquals(6, result.count());
        }
    }

}
