package com.dhayalanit.kakfauserordereventdriven.service.impl;

import com.dhayalanit.kakfauserordereventdriven.dto.UserDto;
import com.dhayalanit.kakfauserordereventdriven.entity.Users;
import com.dhayalanit.kakfauserordereventdriven.repository.UsersRepository;
import com.dhayalanit.kakfauserordereventdriven.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public Long createUser(UserDto userDto) {
        Users users = new Users();
        users.setFirstname(userDto.getFirstname());
        users.setLastname(userDto.getLastname());
        users.setEmail(userDto.getEmail());
        return usersRepository.save(users).getId();
    }

    @Override
    @Transactional
    public void updateUsers(UserDto userDto) {
        this.usersRepository.findById(userDto.getId()).ifPresent(
                users -> {
                    users.setFirstname(userDto.getFirstname());
                    users.setLastname(userDto.getLastname());
                    users.setEmail(userDto.getEmail());
                    this.raiseEvent(userDto);
                }
        );
    }

    private void raiseEvent(UserDto userDto) {
        try {
            String value = OBJECT_MAPPER.writeValueAsString(userDto);
            log.info("ID: {} ", userDto.getId().toString());
            this.kafkaTemplate.sendDefault(userDto.getId(), value);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
