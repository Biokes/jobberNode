package com.jobNode.jobber.web;

import com.jobNode.jobber.dto.request.*;
import com.jobNode.jobber.dto.response.*;
import com.jobNode.jobber.services.interfaces.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/jobberNode")
public class UserController {
    @PostMapping("/customer/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.status(201).body(userService.register(request));
    }
    @PostMapping("/customer/bookService")
    public ResponseEntity<?> bookService(@RequestBody @Valid BookServiceRequest bookServiceRequest){
       return ResponseEntity.status(200).body(ApiResponse.builder().status(OK)
               .data(userService.bookService(bookServiceRequest)).success(true).build());
    }
    @PatchMapping("/customer/cancelBooking")
    public ResponseEntity<?> cancelBooking(@RequestBody @Valid CancelRequest cancelRequest){
        return ResponseEntity.status(200).body(ApiResponse.builder().status(OK)
                .data(userService.cancelRequest(cancelRequest)).success(true).build());
    }
    @GetMapping("/customer/myNotifications/{id}")
    public ResponseEntity<?> notifications(@NotNull @PathVariable(value = "id") Long id){
        return ResponseEntity.status(200).body(ApiResponse.builder()
                .status(OK).data(userService.getNotificationsWith(id)).success(true).build());
    }
    @PostMapping("/customer/review")
    public ResponseEntity<?> dropReview(@Valid @RequestBody ReviewRequest request){
        return ResponseEntity.status(200).body(ApiResponse.builder()
                .status(OK).success(true)
                .data(userService.dropReview(request)).build());
    }
    @GetMapping("/customer/findProviders")
    public ResponseEntity<?> getProviders(@Valid @RequestBody FindServiceRequest request){
        return ResponseEntity.status(200)
                .body(ApiResponse.builder().success(true)
                        .status(OK)
                        .data(userService.findAllByService(request)).build());
    }
    @Autowired
    private UserService userService;

    //TODO:
    //find all providers by location(after integrating google map)
    //get all bookings
}
