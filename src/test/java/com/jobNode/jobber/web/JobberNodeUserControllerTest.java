package com.jobNode.jobber.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobNode.jobber.dto.request.AddressRequest;
import com.jobNode.jobber.dto.request.RegisterRequest;
import com.jobNode.jobber.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class JobberNodeUserControllerTest {
    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void testUserCanRegister() throws Exception {

        String registerRequest = getRegisterRequest();
        log.info("Register Request------------>{}",registerRequest);
        mockMvc.perform(post("/api/v1/jobberNode/customer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerRequest))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    private String getRegisterRequest() throws JsonProcessingException {
        return objectMapper.writeValueAsString(RegisterRequest.builder()
                .email("email@email.com").password("password")
                .address(AddressRequest.builder().housenumber("12").lga("lagos lga")
                        .street("laddo street").state("lagos").build()).fullname("Biokes")
                .username("Abbey kodare").build());
    }
}
