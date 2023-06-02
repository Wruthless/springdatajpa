package com.manning.javapersistence.springdatajpa;


import com.manning.javapersistence.springdatajpa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class QueryResultsTest extends SpringdatajpaApplicationTests {


    @Test
    void testStreamable( ) {
        try (Stream<User> result = userRepository.findByEmailContaining("someother")
                .and(userRepository.findByLevel(2))
                .stream( ).distinct( )) {
            assertEquals(6, result.count( ));
        }
    }

    @Test
    void testNumberOfUsersByActivity( ) {
        int active = userRepository.findNumberOfUsersByActivity(true);
        int inactive = userRepository.findNumberOfUsersByActivity(false);

        assertAll(
                ( ) -> assertEquals(8, active),
                ( ) -> assertEquals(2, inactive)
        );
    }


    @Test
    void testUsersByLevelAndActivity( ) {
        List<User> userList1 = userRepository.findByLevelAndActive(1, true);
        List<User> userList2 = userRepository.findByLevelAndActive(2, true);
        List<User> userList3 = userRepository.findByLevelAndActive(2, false);

        assertAll(
                ( ) -> assertEquals(2, userList1.size( )),
                ( ) -> assertEquals(2, userList2.size( )),
                ( ) -> assertEquals(1, userList3.size( ))
        );
    }

    @Test
    void testFindByAsArrayAndSort( ) {
        List<Object[]> usersList1 = userRepository.findByAsArrayAndSort("ar", Sort.by("username"));
        List<Object[]> usersList2 = userRepository.findByAsArrayAndSort("ar", Sort.by("email_length").descending( ));
        List<Object[]> usersList3 = userRepository.findByAsArrayAndSort("ar", JpaSort.unsafe("email_length"));

        assertAll(
                ( ) -> assertEquals(2, usersList1.size( )),
                ( ) -> assertEquals("darren", usersList1.get(0)[0]),
                ( ) -> assertEquals(21, usersList1.get(0)[1]), // 21 is no. of characters in email.
                ( ) -> assertEquals(2, usersList2.size( )),
                ( ) -> assertEquals("marion", usersList2.get(0)[0]),
                ( ) -> assertEquals("darren", usersList3.get(0)[0])
                );
    }


}
