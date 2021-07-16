package com.vishnuravi.fitbitOMH.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    /**
     * Default welcome message
     * @return String
     */
    @GetMapping
    public String home(){
        return "Fitbit to Open mHealth converter is up and running.";
    }
}
