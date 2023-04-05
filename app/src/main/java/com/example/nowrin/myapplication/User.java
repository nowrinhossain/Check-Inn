package com.example.nowrin.myapplication;



public class User {
    private final String username;
    private final String password;



    private User(UserBuilder builder) {
        this.username = builder.username;
        this.password = builder.password;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }



    public static class UserBuilder {
        private  String username;
        private  String password;




        public UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }





        public User build() {

            User user = new User(this);
            return user;

        }
    }
}