package com.jobNode.jobber.web;

import com.jobNode.jobber.dto.request.RegisterRequest;
import com.jobNode.jobber.dto.response.ApiResponse;
import com.jobNode.jobber.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/jobberNode/customer")
public class UserController {
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ResponseEntity.status(201).body(ApiResponse.builder().status(CREATED)
                .data(userService.register(request)).success(true).build());
    }
    @Autowired
    private UserService userService;
}
