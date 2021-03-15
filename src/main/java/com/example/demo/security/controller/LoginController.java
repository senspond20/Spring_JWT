package com.example.demo.security.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    
    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }
    
    @PostMapping(value = "/doLogin")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        // if (error != null) {
        //     model.addObject("error", "Invalid username and password!");
        // }
    
        // if (logout != null) {
        //     model.addObject("msg", "You've been logged out successfully.");
        // }
        model.setViewName("/login");
        return model;
    }
    
    @GetMapping("/doLogout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "login";
    }
}