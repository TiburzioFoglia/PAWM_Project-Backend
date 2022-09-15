package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;

@Data
public class JwtRequest {

    private String userName;
    private String userPassword;

}
