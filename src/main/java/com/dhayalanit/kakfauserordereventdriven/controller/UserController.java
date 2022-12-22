package com.dhayalanit.kakfauserordereventdriven.controller;

import com.dhayalanit.kakfauserordereventdriven.dto.UserDto;
import com.dhayalanit.kakfauserordereventdriven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Long createUser(@RequestBody UserDto userDto) {
        return this.userService.createUser(userDto);
    }


    @PutMapping("/update")
    public void updateUserval(@RequestBody UserDto userDto) {
        this.userService.updateUsers(userDto);
    }

}
