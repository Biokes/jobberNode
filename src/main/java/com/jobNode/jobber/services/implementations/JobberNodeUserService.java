package com.jobNode.jobber.services.implementations;

import com.jobNode.jobber.data.models.enums.Status;
import com.jobNode.jobber.data.models.models.*;
import com.jobNode.jobber.data.repository.*;
import com.jobNode.jobber.dto.request.*;
import com.jobNode.jobber.dto.response.*;
import com.jobNode.jobber.exception.JobberNodeException;
import com.jobNode.jobber.services.interfaces.UserService;
import com.jobNode.jobber.services.interfaces.ProvidersServices;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

import static com.jobNode.jobber.data.models.enums.RegisterationState.PENDING;
import static com.jobNode.jobber.data.models.enums.Role.CUSTOMER;
import static com.jobNode.jobber.exception.ExceptionMessages.BOOKED;
import static com.jobNode.jobber.exception.ExceptionMessages.INVALID_DETAILS;
import static java.time.LocalDateTime.now;

@Service
public class JobberNodeUserService implements UserService {
    @Override
    @Transactional
    public RegisterResponse register(@Valid RegisterRequest request){
        User user = mapUser(request);
        return modelMapper.map(user,RegisterResponse.class);
    }

    @Override
    @Transactional
    public List<ProviderResponse> findAllByService(@Valid FindServiceRequest seviceRequest) {
        Optional<User> optionalUser =userRepository.findById(seviceRequest.getUserId());
        if (optionalUser.isEmpty()) throw new JobberNodeException(INVALID_DETAILS.getMessage());
        return providersServices.findAllByService(seviceRequest.getService());
    }

    @Override
    @Transactional
    public BookResponse bookService(@Valid BookServiceRequest bookRequest) {//id = user, providerId = provider
        CustomerOrder customerOrder = buildOrder(bookRequest);
        customerOrder = orderRepo.save(customerOrder);
        Notification notification = createNotification(bookRequest);
        notificationRepo.save(notification);
        return BookResponse.builder().orderId(customerOrder.getId())
                .providerId(bookRequest.getProviderId()).customerId(bookRequest.getId())
                .status(customerOrder.getStatus()).timeStamp(customerOrder.getTimeStamp())
                .bookingMessage(BOOKED.getMessage()).build();
    }

    @Override
    @Transactional
    public BookResponse cancelRequest(@Valid CancelRequest cancelRequest) {
        CustomerOrder order = getCustomerOrder(cancelRequest);
        notifyUserAndProvider(cancelRequest);
        return getBookResponse(cancelRequest, order);
    }
    @Override
    @Transactional
    public List<NotificationResponse> getNotificationsWith(Long id){
        return notificationRepo.findAllByUserId(id).stream()
                .map(notification -> modelMapper.map(notification, NotificationResponse.class))
                .toList();
    }
    @Override
    @Transactional
    public ReviewResponse dropReview(ReviewRequest request)
    {
        Providers providers =providerRepo.findById(request.getProviderId()).get();
        User user = userRepository.findById(request.getUserId()).get();
        Review review = buildReview(request, providers, user);
        review = reviewRepo.save(review);
        return buildReviewResponse(review);
    }

    @Override
    public User findUserByEmail(String username) {
        return userRepository.findByEmail(username).get();
    }

    private static Review buildReview(ReviewRequest request, Providers providers, User user) {
        return Review.builder()
                .provider(providers)
                .description(request.getDescription())
                .reviewer(user)
                .build();
    }
    private static ReviewResponse buildReviewResponse(Review review) {
        return ReviewResponse.builder()
                .reviewNote(review.getDescription())
                .providerId(review.getProvider().getId())
                .timeStamp(review.getTimeCreated())
                .reviewId(review.getId())
                .userId(review.getReviewer().getId()).build();
    }
    private void notifyUserAndProvider(CancelRequest cancelRequest) {
        Notification notification= notifyUser(cancelRequest.getUserId(), cancelRequest.getReason());
        notificationRepo.save(notification);
        notification = notifyUser(providerRepo.
                        findById(cancelRequest.getProviderId()).get().getUser().getId(),
                        cancelRequest.getReason());
        notificationRepo.save(notification);
    }
    private Notification notifyUser(Long userId, String description) {
        return Notification.builder()
                .user(userRepository.findById(userId).get())
                .description(description)
                .build();
    }
    private CustomerOrder buildOrder(BookServiceRequest bookRequest){
        return CustomerOrder.builder()
                .status(Status.PENDING)
                .customer(userRepository.findById(bookRequest.getId()).get())
                .provider(providerRepo.findById(bookRequest.getProviderId()).get())
                .build();
    }
    private CustomerOrder getCustomerOrder(CancelRequest cancelRequest) {
        CustomerOrder order = orderRepo.findById(cancelRequest.getOrderId()).get();
        order.setStatus(Status.TERMINATED);
        order.setTimeUpdated(now());
        orderRepo.save(order);
        return order;
    }
    private Notification createNotification(BookServiceRequest bookRequest){
        return Notification.builder()
                .user(providerRepo.findById(bookRequest.getProviderId()).get().getUser())
                .description(bookRequest.getDescription())
                .build();
    }
    private static BookResponse getBookResponse(CancelRequest cancelRequest, CustomerOrder order) {
        return BookResponse.builder().customerId(cancelRequest.getUserId())
                .timeUpdated(order.getTimeUpdated()).providerId(cancelRequest.getProviderId())
                .orderId(order.getId()).status(order.getStatus()).build();
    }
    private User mapUser(RegisterRequest request){
        Address address = modelMapper.map(request.getAddress(), Address.class);
        User user = modelMapper.map(request,User.class);
        user.setAddress(address);
        user.setRole(CUSTOMER);
        user.setRegisterationState(PENDING);
        user.setPassword(encoder.encode(user.getPassword()));
        user= userRepository.save(user);
        return user;
    }
    @Autowired
    public JobberNodeUserService(ModelMapper modelMapper, UserRepository userReepository,
                                 NotificationRepository notificationRepo, PasswordEncoder encoder,
                                 ProvidersServices providersServices, ProviderRepository providerRepo,
                                 OrderRepository orderRepo, ReviewRepository reviewRepo)
    {
        this.orderRepo= orderRepo;
        this.modelMapper= modelMapper;
        this.userRepository= userReepository;
        this.encoder = encoder;
        this.providersServices = providersServices;
        this.providerRepo = providerRepo;
        this.notificationRepo = notificationRepo;
        this.reviewRepo = reviewRepo;
    }
    private final OrderRepository orderRepo;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ProvidersServices providersServices;
    private final PasswordEncoder encoder;
    private final ProviderRepository providerRepo;
    private final NotificationRepository notificationRepo;
    private final ReviewRepository reviewRepo;
}
