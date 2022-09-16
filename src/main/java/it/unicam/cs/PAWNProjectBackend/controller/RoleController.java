package it.unicam.cs.PAWNProjectBackend.controller;


import it.unicam.cs.PAWNProjectBackend.model.Role;
import it.unicam.cs.PAWNProjectBackend.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/createNewRole")
    public Role createNewRole(@RequestBody Role role) {
        return roleService.createNewRole(role);
    }


}
