package com.jobNode.jobber.services.interfaces;

import com.jobNode.jobber.data.models.enums.Services;
import com.jobNode.jobber.dto.request.*;
import com.jobNode.jobber.dto.response.*;


import java.util.List;

public interface ProvidersServices {
    List<ProviderResponse> findAllByService(Services service);
    ProviderResponse register(ProviderRequest providerRequest);
    List<NotificationResponse> notifications(Long l);
    BookResponse acceptOffer(OfferRequest acceptRequest);
    List<ReviewResponse> getReviews(long l);
    BookResponse terminateOrder(TerminateOfferRequest offer);
}
