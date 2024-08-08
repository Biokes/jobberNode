package com.jobNode.jobber.services.interfaces;

import com.jobNode.jobber.dto.request.*;
import com.jobNode.jobber.dto.response.*;
import com.jobNode.jobber.request.*;
import com.jobNode.jobber.response.*;

import java.util.List;

public interface UserService {
    RegisterResponse register(RegisterRequest request);
    List<ProviderResponse> findAllByService(FindServiceRequest seviceRequest);
    BookResponse bookService(BookServiceRequest bookRequest);
    BookResponse cancelRequest(CancelRequest cancelRequest);
    List<NotificationResponse> getNotificationsWith(Long id);
    ReviewResponse dropReview(ReviewRequest review);
}
