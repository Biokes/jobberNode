package com.jobNode.jobber.data.repository;

import com.jobNode.jobber.data.models.models.Providers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Providers,Long> {
}
