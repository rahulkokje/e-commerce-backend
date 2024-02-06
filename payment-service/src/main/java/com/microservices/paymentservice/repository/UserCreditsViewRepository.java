package com.microservices.paymentservice.repository;

import com.microservices.paymentservice.model.UserCreditsView;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCreditsViewRepository extends ReactiveCrudRepository<UserCreditsView, String> {
}
