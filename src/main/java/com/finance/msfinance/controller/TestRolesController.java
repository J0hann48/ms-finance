package com.finance.msfinance.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class TestRolesController {

    @GetMapping("/accessAdmin")
    public String accessAdmin(){
        return "Hola, has accedido con rol de Admin";
    }
    @GetMapping("/accessUser")
    public String accessUser(){
        return "Hola, has accedido con rol de User";
    }

    @GetMapping("/accessInvited")
    public String accessInvited(){
        return "Hola, has accedido con rol de Invited";
    }
}
