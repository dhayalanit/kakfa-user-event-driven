package com.dhayalanit.kakfauserordereventdriven.service;

import com.dhayalanit.kakfauserordereventdriven.dto.UserDto;
import com.dhayalanit.kakfauserordereventdriven.entity.Users;

public interface UserService {
    Long createUser(UserDto userDto);
    void updateUsers(UserDto userDto);
}
