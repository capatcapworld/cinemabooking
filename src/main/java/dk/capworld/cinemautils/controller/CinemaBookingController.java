package dk.capworld.cinemautils.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaBookingController {
    @GetMapping("/")
    public String hello() {
        return "Hello, Spring Boot!";
    }
}
