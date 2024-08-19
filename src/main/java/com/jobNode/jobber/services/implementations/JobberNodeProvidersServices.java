package com.jobNode.jobber.services.implementations;

import com.jobNode.jobber.data.models.models.*;
import com.jobNode.jobber.data.models.enums.Services;
import com.jobNode.jobber.data.repository.*;
import com.jobNode.jobber.dto.request.OfferRequest;
import com.jobNode.jobber.dto.request.ProviderRequest;
import com.jobNode.jobber.dto.request.TerminateOfferRequest;
import com.jobNode.jobber.dto.response.*;
import com.jobNode.jobber.services.interfaces.ProvidersServices;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jobNode.jobber.data.models.enums.RegisterationState.PENDING;
import static com.jobNode.jobber.data.models.enums.Role.PROVIDER;
import static com.jobNode.jobber.data.models.enums.Status.ACCEPTED;
import static java.time.LocalDateTime.now;

@Service
@Transactional
public class JobberNodeProvidersServices implements ProvidersServices {
    @Override
    @Transactional
    public List<ProviderResponse> findAllByService(Services service) {
        List<Providers> providersList = repository.findAll().stream()
                .filter(providers -> providers.getServices()
                        .contains(service)).toList();
       return providersList.stream().map(user-> modelMapper.map(user, ProviderResponse.class)).toList();
    }
    @Override
    @Transactional
    public List<NotificationResponse> notifications(Long id){
        id = repository.findById(id).get().getUser().getId();
        return notificationRepo.findAllByUserId(id).stream()
                .map(notification -> modelMapper.map(notification, NotificationResponse.class))
                .toList();
    }
    @Override
    @Transactional
    public BookResponse acceptOffer(OfferRequest acceptRequest){
        CustomerOrder order =orderRepo.findById(acceptRequest.getOrderId()).get();
        order.setStatus(ACCEPTED);
        order.setTimeUpdated(now());
        orderRepo.save(order);
        sendNotificationTo(order.getProvider(),order.getCustomer());
        return BookResponse.builder().customerId(order.getCustomer().getId())
                .bookingMessage("Job Offer Accepted.").timeStamp(order.getTimeStamp())
                .timeUpdated(order.getTimeUpdated()).providerId(order.getProvider().getId())
                .orderId(order.getId()).status(order.getStatus()).build();
    }

    private void sendNotificationTo(Providers provider, User customer) {
        sendNotificationTo(provider);
        sendNotificationTo(customer);
    }

    private void sendNotificationTo(Providers provider) {
        Notification notification = new Notification(null, provider.getUser(),now(),
                "You Accepted an order",false);
        notificationRepo.save(notification);
    }
    private void sendNotificationTo(User user){
        Notification notification = new Notification(null, user,now(),
                "You Booking Has been Accepted",false);
        notificationRepo.save(notification);
    }

    @Override
    public List<ReviewResponse> getReviews(long id) {
        List<Review> reviews =  reviewRepo.findAllByProviderId(id);
        return reviews.stream().map(review->ReviewResponse.builder()
                .providerId(review.getProvider().getId())
                .reviewNote(review.getDescription())
                .timeStamp(review.getTimeCreated())
                .reviewId(review.getId())
                .userId(review.getReviewer().getId())
                .build()).toList();
    }

    @Override
    public BookResponse terminateOrder(TerminateOfferRequest offer) {
        return null;
    }

    @Override
    @Transactional
    public ProviderResponse register(@Valid ProviderRequest providerRequest){
        User user = modelMapper.map(providerRequest.getRequest(),User.class);
        Providers providers = createProvider(providerRequest, userRepository.save(user));
        RegisterResponse registerResponse = modelMapper.map(providers.getUser(), RegisterResponse.class);
        return getProviderResponse(registerResponse, providers);
    }
    private static ProviderResponse getProviderResponse(RegisterResponse registerResponse, Providers providers) {
        return ProviderResponse.builder()
                .response(registerResponse)
                .id(providers.getId())
                .services(providers.getServices()).build();
    }
    private Providers createProvider(ProviderRequest providerRequest, User user){
        Providers providers = new Providers();
        providers.setServices(providerRequest.getServicesList());
        providers.setUser(user);
        providers.getUser().setRegisterationState(PENDING);
        providers.getUser().setAddress(modelMapper.map(providerRequest.getRequest().getAddress(), Address.class));
        providers.getUser().setRole(PROVIDER);
        providers.getUser().setPassword(encoder.encode(user.getPassword()));
        providers = repository.save(providers);
        return providers;
    }
    @Autowired
    private NotificationRepository notificationRepo;
    @Autowired
    private ProviderRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ReviewRepository reviewRepo;
}
