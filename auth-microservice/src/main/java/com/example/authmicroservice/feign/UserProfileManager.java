package com.example.authmicroservice.feign;

import com.example.authmicroservice.config.RestApis;
import com.example.authmicroservice.dto.UserProfileRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8081/dev/v1/user-profile", name = "userProfileManager")
public interface UserProfileManager {

    @PostMapping(RestApis.CREATE_USER)
    ResponseEntity<UserProfileRequestDto> createUser(@RequestBody UserProfileRequestDto userProfileRequestDto);
}

/*
    createUser method from user-microservice
*/