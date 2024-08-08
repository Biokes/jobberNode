package com.jobNode.jobber.data.repository;

import com.jobNode.jobber.data.models.models.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
//    List<CustomerOrder> findByProviderIdAndCustomer_Id(Long providerId, Long userId);
}
