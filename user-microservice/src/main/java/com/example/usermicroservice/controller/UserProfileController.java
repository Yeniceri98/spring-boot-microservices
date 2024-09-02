package com.example.usermicroservice.controller;

import com.example.usermicroservice.config.RestApis;
import com.example.usermicroservice.document.UserProfile;
import com.example.usermicroservice.dto.CreateUserRequestDto;
import com.example.usermicroservice.dto.UserProfileResponseDto;
import com.example.usermicroservice.service.UserProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "UserController", description = "UserController Operations")
@RestController
@RequestMapping(RestApis.USERPROFILE)
public class UserProfileController {
    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    @Tag(name = "Create User")
    @PostMapping(RestApis.CREATE_USER)
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequestDto createUserDto) {
        service.createUser(createUserDto);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @Tag(name = "Get All Users")
    @GetMapping(RestApis.GET_ALL)
    public ResponseEntity<List<UserProfileResponseDto>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
}
