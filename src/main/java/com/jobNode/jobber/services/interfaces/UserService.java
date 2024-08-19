package com.jobNode.jobber.services.interfaces;

import com.jobNode.jobber.data.models.models.User;
import com.jobNode.jobber.dto.request.*;
import com.jobNode.jobber.dto.response.*;
import jakarta.validation.Valid;


import java.util.List;

public interface UserService {
    RegisterResponse register(@Valid RegisterRequest request);
    List<ProviderResponse> findAllByService(@Valid FindServiceRequest seviceRequest);
    BookResponse bookService(@Valid BookServiceRequest bookRequest);
    BookResponse cancelBooking(@Valid CancelRequest cancelRequest);
    List<NotificationResponse> getNotificationsWith(@Valid Long id);
    ReviewResponse dropReview(@Valid ReviewRequest review);
    User findUserByEmail(String username);
}
