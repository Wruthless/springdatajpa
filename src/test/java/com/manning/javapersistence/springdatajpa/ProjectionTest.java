package com.manning.javapersistence.springdatajpa;


import com.manning.javapersistence.springdatajpa.model.User;
import com.manning.javapersistence.springdatajpa.repositories.Projection;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProjectionTest extends SpringdatajpaApplicationTests {


    @Test
    void testProjectionUsername( ) {

        List<Projection.UsernameOnly> users = userRepository.findByEmail("john@somedomain.com");

        assertAll(
                ( ) -> assertEquals(1, users.size( )),
                ( ) -> assertEquals("john", users.get(0).getUsername( ))
        );
    }

    // Replace above with generic test.
    @Test
    void dynamicTestProjectionUsername() {

    }

    @Test
    void testProjectionUserSummary( ) {
        List<Projection.UserSummary> users = userRepository.
                findByRegistrationDateAfter(LocalDate.of(2021, Month.FEBRUARY, 1));

        assertAll(
                ( ) -> assertEquals(1, users.size( )),
                ( ) -> assertEquals("julius", users.get(0).getUsername( )),
                ( ) -> assertEquals("julius julius@someotherdomain.com", users.get(0).getInfo( ))
        );
    }

    @Test
    void testDynamicProjection( ) {
        List<Projection.UsernameOnly> usernames = userRepository.
                findByEmail("mike@somedomain.com", Projection.UsernameOnly.class);
        List<User> users = userRepository.findByEmail("mike@somedomain.com", User.class);

        assertAll(
                ( ) -> assertEquals(1, usernames.size( )),
                ( ) -> assertEquals("mike", usernames.get(0).getUsername( )),
                ( ) -> assertEquals(1, users.size( )),
                ( ) -> assertEquals("mike", users.get(0).getUsername( ))
        );
    }

    // Custom test just using the generic projection method.
    @Test
    void replaceWithGenericProjection( ) {
        List<Projection.UserSummary> userSummaries = userRepository
                .findByEmail("mike@somedomain.com",Projection.UserSummary.class);
    }


}
