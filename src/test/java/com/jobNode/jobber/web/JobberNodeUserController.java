package com.jobNode.jobber.web;

import com.jobNode.jobber.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class JobberNodeUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Test
    void testUserCaRegister(){}
}
