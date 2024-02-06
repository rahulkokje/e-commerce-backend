package com.microservices.paymentservice.service;

import com.microservices.paymentservice.model.UserCreditsView;
import com.microservices.paymentservice.repository.UserCreditsViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserCreditsViewService {

    private final UserCreditsViewRepository repository;

    public Mono<UserCreditsView> getUserCredits(String userId) {
        return repository.findById(userId);
    }
}
