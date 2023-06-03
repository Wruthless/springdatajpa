package com.manning.javapersistence.springdatajpa.repositories;


import com.manning.javapersistence.springdatajpa.model.User;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;


public class Projection {


    public interface UserSummary {

        // Return the username field.
        String getUsername( );

        // Returns the concatenation of the username field, a space, and the email field.
        @Value("#{target.username} #{target.email}")
        String getInfo( );


    }

    public static class UsernameOnly {

        private String username;

        public UsernameOnly(String username) {
            this.username = username;
        }

        public String getUsername( ) {
            return username;
        }


    }

    public static <T> List<T> convertToProjection(List<User> users, Class<T> type) {

        List<T> projections = new ArrayList<>( );

        for (User user : users) {
            if (type == UserSummary.class) {
                UserSummary userSummary = new UserSummary( ) {
                    @Override
                    public String getUsername( ) {
                        return user.getUsername( );
                    }

                    @Override
                    public String getInfo( ) {
                        return user.getUsername( ) + " " + user.getEmail( );
                    }
                };
                projections.add(type.cast(userSummary));
            } else if (type == UsernameOnly.class) {
                UsernameOnly usernameOnly = new UsernameOnly(user.getUsername( ));
                projections.add(type.cast(usernameOnly));
            }
        }
        return projections;
    }

}
