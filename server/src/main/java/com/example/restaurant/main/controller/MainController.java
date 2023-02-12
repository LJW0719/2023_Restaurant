package com.example.restaurant.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String main(){
        System.out.println(1);
        return "main/index";
    }
}
