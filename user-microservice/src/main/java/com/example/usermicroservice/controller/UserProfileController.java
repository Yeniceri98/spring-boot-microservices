package com.example.usermicroservice.controller;

import com.example.usermicroservice.config.RestApis;
import com.example.usermicroservice.dto.UserProfileRequestDto;
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
    public ResponseEntity<UserProfileRequestDto> createUser(@RequestBody UserProfileRequestDto userProfileRequestDto) {
        return new ResponseEntity<>(service.createUser(userProfileRequestDto), HttpStatus.CREATED);
    }

    @Tag(name = "Get All Users")
    @GetMapping(RestApis.GET_ALL)
    public ResponseEntity<List<UserProfileResponseDto>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @Tag(name = "Delete User")
    @DeleteMapping(RestApis.DELETE_USER)
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        service.deleteUser(id);
        return new ResponseEntity<>("User is deleted with id " + id, HttpStatus.OK);
    }
}
