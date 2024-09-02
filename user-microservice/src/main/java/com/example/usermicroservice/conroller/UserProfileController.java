package com.example.usermicroservice.conroller;

import com.example.usermicroservice.config.RestApis;
import com.example.usermicroservice.document.UserProfile;
import com.example.usermicroservice.dto.CreateUserRequestDto;
import com.example.usermicroservice.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestApis.USERPROFILE)
public class UserProfileController {
    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    @PostMapping(RestApis.CREATE_USER)
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequestDto createUserDto) {
        service.createUser(createUserDto);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @GetMapping(RestApis.GET_ALL)
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
}
