package com.jobNode.jobber.service;

import com.jobNode.jobber.dto.request.*;
import com.jobNode.jobber.dto.response.BookResponse;
import com.jobNode.jobber.dto.response.ProviderResponse;
import com.jobNode.jobber.dto.response.RegisterResponse;
import com.jobNode.jobber.dto.response.ReviewResponse;
import com.jobNode.jobber.services.interfaces.ProvidersServices;
import com.jobNode.jobber.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.jobNode.jobber.data.models.enums.RegisterationState.PENDING;
import static com.jobNode.jobber.data.models.enums.Services.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class JobbernodeServiceTest {
    @Test
    void testUserCanRegister(){
        RegisterRequest request = getRegisterRequest();
        RegisterResponse response =userService.register(request);
        confirmRegister(response);
    }
    @Test
    void testUserCanFindAllByService(){
        FindServiceRequest seviceRequest = FindServiceRequest.builder()
                .userId(1L)
                .service(PLUMBING)
                .build();
        assertEquals(0,userService.findAllByService(seviceRequest).size());
    }
    @Test
    void testUserCanBookService() {
        ProviderRequest providerRequest = buildProviderRequest("abbeyOlowo134@gmail.com");
        ProviderResponse providerResponse = providerService.register(providerRequest);
        assertNotNull(providerResponse);
        log.info("PROVIDER-RESPONSE ------------------>{}",providerResponse);
        BookServiceRequest bookRequest = getBookRequest(providerResponse);
        BookResponse response = userService.bookService(bookRequest);
        log.info("RESPONSE --------------------->{}",response);
        assertEquals(1, providerService.notifications(providerResponse.getId()).size());
        assertNotNull(response);
    }
    @Test
    void testUserCanSendAndCancelRequest(){
        ProviderRequest providerRequest =  buildProviderRequest("AbbeyOlowo2@Gmail.com");
        ProviderResponse response = providerService.register(providerRequest);
        log.info("RESPONSE --------------------->{}",response);
        assertEquals(0,providerService.notifications(response.getId()).size());
        BookServiceRequest bookRequest = getBookRequest(response);
        var bookResponse = userService.bookService(bookRequest);
        log.info("BOOKResponse --------------------->{}",bookResponse);
        assertEquals(1,providerService.notifications(response.getId()).size());
        CancelRequest cancelRequest = CancelRequest.builder().orderId(bookResponse.getOrderId())
                .providerId(response.getId()).userId(1L).build();
        bookResponse = userService.cancelRequest(cancelRequest);
        log.info("BOOKResponse Canceled --------------------->{}",bookResponse);
        assertNotNull(bookResponse);
        assertEquals(2,providerService.notifications(response.getId()).size());
        bookRequest = getBookRequest(response);
        bookResponse = userService.bookService(bookRequest);
        log.info("BOOKResponse --------------------->{}",bookResponse);
        assertEquals(3,providerService.notifications(response.getId()).size());
        AcceptRequest acceptRequest = AcceptRequest.builder().orderId(bookResponse.getOrderId()).build();
        BookResponse acceptedOffer = providerService.acceptOffer(acceptRequest);
        assertNotNull(response);
        assertEquals(2,userService .getNotificationsWith(1L).size());
    }
    @Test
    void testCustomersCanDropReviews(){
        ReviewRequest review = ReviewRequest.builder()
                .description("well Done").userId(1L).providerId(17L).build();
        ReviewResponse response = userService.dropReview(review);
        assertNotNull(response);
        log.info("Review Response ----------------->{}",response);
        assertEquals(1,providerService.getReviews(17L).size());
    }
    private static BookServiceRequest getBookRequest(ProviderResponse providerResponse){
        return BookServiceRequest.builder().id(1L)
                .service(PLUMBING)
                .providerId(providerResponse.getId())
                .proposedDate(LocalDate.parse("2007-04-12"))
                .build();
    }
    private static ProviderRequest buildProviderRequest(String mail){
        return ProviderRequest.builder()
                .servicesList(List.of(PLUMBING,NURSING,PRINTING))
                .request(
                        RegisterRequest.builder()
                                .fullname("Abbey olowo").password("Passwor12,")
                                .username("Abbeykodara").email(mail)
                                .address(AddressRequest.builder().housenumber("5")
                                        .street("akinshilo").state("lagos").lga("alimosho")
                                        .build()).build()).build();
    }
    private static void confirmRegister(RegisterResponse response){
        assertNotNull(response);
        assertEquals(PENDING, response.getRegisterationState());
        assertNotNull(response.getUsername());
        assertNotNull(response.getEmail());
        assertNotNull(response.getTimeStamp());
    }
    private static RegisterRequest getRegisterRequest(){
        return RegisterRequest.builder()
                .fullname("Abbey olowo")
                .password("Passwor12,")
                .username("Abbey olowo")
                .email("abbeyOlowo@gmail.com")
                .address(AddressRequest.builder()
                        .housenumber("5")
                        .street("akinshilo")
                        .state("lagos")
                        .lga("alimosho")
                        .build())
                .build();
    }
    @Autowired
    private UserService userService;
    @Autowired
    private ProvidersServices providerService;
}




