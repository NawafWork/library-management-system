package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/branches")
    public String branches() {
        return "branches";
    }
    
    @GetMapping("/librarians")
    public String librarians() {
        return "librarians";
    }
    
    @GetMapping("/books")
    public String books() {
        return "books";
    }
    
    @GetMapping("/members")
    public String members() {
        return "members";
    }
    
    @GetMapping("/loans")
    public String loans() {
        return "loans";
    }
}
