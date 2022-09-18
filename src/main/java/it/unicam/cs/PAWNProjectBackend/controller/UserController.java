package it.unicam.cs.PAWNProjectBackend.controller;

import it.unicam.cs.PAWNProjectBackend.model.User;
import it.unicam.cs.PAWNProjectBackend.service.DBMSController;
import it.unicam.cs.PAWNProjectBackend.service.HandlerPrenotazione;
import it.unicam.cs.PAWNProjectBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final HandlerPrenotazione handlerPrenotazione;
    private final DBMSController dbmsController;

    @PostMapping("/registerNewUser")
    public User registerNewUser(@RequestBody User user){
        return this.userService.registerNewUser(user);
    }

    @GetMapping("/forAdmin")
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to admin";
    }

    @GetMapping("/forUser")
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }

    @GetMapping("/hasReservations")
    @PreAuthorize("hasRole('User')")
    public String hasReservations(@RequestParam String userName){
        User user = this.dbmsController.getUserByUserName(userName);
        return this.handlerPrenotazione.hasReservations(user);
    }


}
