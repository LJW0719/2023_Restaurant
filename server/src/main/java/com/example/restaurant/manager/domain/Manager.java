package com.example.restaurant.manager.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Manager {
    private Long id;
    private String email;
    private String password;
    private String name;

    private String number;
    private String restaurant;
    private String address;

    public Manager(String email, String password, String name, String number, String restaurant, String address){
        this.email=email;
        this.password=password;
        this.name=name;
        this.number=number;
        this.restaurant=restaurant;
        this.address=address;
    }
}