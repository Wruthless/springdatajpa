package com.manning.javapersistence.springdatajpa.repositories;


import com.manning.javapersistence.springdatajpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsername(String username);

    List<User> findAllByOrderByUsernameAsc( );

    List<User> findByRegistrationDateBetween(LocalDate start, LocalDate end);

    List<User> findByUsernameAndEmail(String username, String email);

    List<User> findByUsernameOrEmail(String username, String email);

    List<User> findByUsernameIgnoreCase(String username);

    List<User> findByLevelOrderByUsernameDesc(int level);

    List<User> findByLevelGreaterThanEqual(int level);

    List<User> findByUsernameContaining(String text);

    List<User> findByUsernameLike(String text);

    List<User> findByUsernameStartingWith(String start);

    List<User> findByUsernameEndingWith(String end);

    List<User> findByActive(boolean active);

    List<User> findByRegistrationDateIn(Collection<LocalDate> dates);

    List<User> findByRegistrationDateNotIn(Collection<LocalDate> dates);

    User findFirstByOrderByUsernameAsc();

    User findTopByOrderByRegistrationDateDesc();

    @Override
    Page<User> findAll(Pageable pageable);

    List<User> findFirst2ByLevel(int level, Sort sort);

    List<User> findByLevel(int level, Sort sort);

    List<User> findByActive(boolean active, Pageable pageable);

    Streamable<User> findByEmailContaining(String text);

    Streamable<User> findByLevel(int level);

    // Custom queries.

    // Return the number of active users.
    @Query("select count(u) from User u where u.active = ?1")
    int findNumberOfUsersByActivity(boolean active);

    // Return users with the level and active status given as named parameters.
    @Query("select u from User u where u.level = :level and u.active = :active")
    List<User> findByLevelAndActive(@Param("level") int level, @Param("active") boolean active);

    // Returns the number of users with a given active status. This query is written in
    // native SQL (nativeQuery = true).
    @Query(value = "SELECT COUNT(*) FROM USERS WHERE ACTIVE = ?1", nativeQuery = true)
    int findNumberOfUsersByActivityNative(boolean active);

    // Return a list of arrays, with each array containing the username and the length of the email
    // after filtering based on the username. The second Sort parameter is used to sort the results.
    @Query("select u.username, LENGTH(u.email) as email_length from #{#entityName} u where u.username like %?1%")
    List<Object[]> findByAsArrayAndSort(String text, Sort sort);

    <T> List<T> findByEmail(String username, Class<T> type);

    List<Projection.UserSummary> findByRegistrationDateAfter(LocalDate date);

    List<Projection.UsernameOnly> findByEmail(String username);
}
