package com.manning.javapersistence.springdatajpa.repositories;


import org.springframework.beans.factory.annotation.Value;


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


}
