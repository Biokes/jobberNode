package com.jobNode.jobber.data.repository;

import com.jobNode.jobber.data.models.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByProviderId(Long id);
}
