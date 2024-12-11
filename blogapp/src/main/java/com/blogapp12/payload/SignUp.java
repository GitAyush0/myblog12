package com.blogapp12.payload;

import lombok.Data;

@Data

public class SignUp {

    private String name;
    private String email;
    private String username;
    private String password;
    private String userRole;

}