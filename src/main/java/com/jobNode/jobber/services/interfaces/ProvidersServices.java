package com.jobNode.jobber.services.interfaces;

import com.jobNode.jobber.data.models.enums.Services;
import com.jobNode.jobber.dto.request.AcceptRequest;
import com.jobNode.jobber.dto.request.ProviderRequest;
import com.jobNode.jobber.dto.response.BookResponse;
import com.jobNode.jobber.dto.response.NotificationResponse;
import com.jobNode.jobber.dto.response.ProviderResponse;
import com.jobNode.jobber.dto.response.ReviewResponse;

import java.util.List;

public interface ProvidersServices {
    List<ProviderResponse> findAllByService(Services service);
    ProviderResponse register(ProviderRequest providerRequest);

    List<NotificationResponse> notifications(Long l);
    BookResponse acceptOffer(AcceptRequest acceptRequest);
    List<ReviewResponse> getReviews(long l);
}
