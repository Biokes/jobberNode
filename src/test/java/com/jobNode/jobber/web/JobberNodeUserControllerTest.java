package com.jobNode.jobber.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobNode.jobber.data.models.enums.Services;
import com.jobNode.jobber.dto.request.AddressRequest;
import com.jobNode.jobber.dto.request.BookServiceRequest;
import com.jobNode.jobber.dto.request.LoginRequest;
import com.jobNode.jobber.dto.request.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.jobNode.jobber.data.models.enums.Services.PLUMBING;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class JobberNodeUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private String token ="eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6WyJDVVNUT01FUiJdLCJleHAiOjE3MjM1OTk3NTEsImlzcyI6IkpvYmJlck5vZGUifQ.jlNk1OVaB-pQ-Di40Wrq43DQ-X_UeZnhgZC0uBdSm0WdaEU9AeJ8qmHkvIV-hLaPG4ZMxTf6RQ5cnDTTdoDXtA";
    @Test
    void testUserCanRegister() throws Exception {
        String registerRequest = getRegisterRequest("email34028@email.com");
        log.info("Register Request------------>{}",registerRequest);
        mockMvc.perform(post("/api/v1/jobberNode/customer/register")
                        .content(registerRequest)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    void registerWithInvalidDetails() throws Exception{
        String registerRequest = getRegisterRequest("weked");
        log.info("Register Request------------>{}",registerRequest);
        mockMvc.perform(post("/api/v1/jobberNode/customer/register")
                        .contentType(APPLICATION_JSON)
                        .content(registerRequest))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    void testCustomerCanBookService() throws Exception{
        String request = objectMapper.writeValueAsString(
                BookServiceRequest.builder().service(Services.NURSING).id(74L)
                        .proposedDate(LocalDate.parse("2002-10-10"))
                        .description("house warming").providerId(17L).build());
        mockMvc.perform(post("/api/v1/jobberNode/customer/bookService")
                        .header("Authorization", "Bearer " + token)
                .content(request).contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
        mockMvc.perform(post("/api/v1/jobberNode/customer/bookService")
                .content(objectMapper.writeValueAsString(BookServiceRequest.builder()
                        .id(74L).service(PLUMBING).providerId(0L)
                        .build())).contentType(APPLICATION_JSON)
                        .header("Authourization","Bearer "+ token))
                .andExpect(status().isBadRequest()).andDo(print());
    }
    @Test
    void testCustomerCanLogin()throws Exception{
        LoginRequest request = LoginRequest.builder().email("email34028@email.com")
                .password("Password12,").build();
        mockMvc.perform(post("/api/v1/jobberNode/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }
    @Test
    void testCustomerInvalidLoginDetails()throws Exception{
        mockMvc.perform(post("/api/v1/jobberNode/login")
                        .content(objectMapper.writeValueAsString(LoginRequest.builder()
                                .email("l").password("").build()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andDo(print());
    }
    private String getRegisterRequest(String mail) throws JsonProcessingException {
        return objectMapper.writeValueAsString(RegisterRequest.builder()
                .email(mail).password("Password12,")
                .address(AddressRequest.builder().housenumber("12").lga("lagos lga")
                        .street("laddo street").state("lagos").build()).fullname("Biokes")
                .username("Abbey kodare").build());
    }
}
