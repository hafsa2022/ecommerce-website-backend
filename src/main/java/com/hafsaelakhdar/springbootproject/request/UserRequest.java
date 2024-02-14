package com.hafsaelakhdar.springbootproject.request;

import com.hafsaelakhdar.springbootproject.entities.Role;
import lombok.Data;

@Data
public class UserRequest {
    private Integer id;

    private String username;

    private String email;

    private String password;

    private Role role;

    private byte[] img;
}
