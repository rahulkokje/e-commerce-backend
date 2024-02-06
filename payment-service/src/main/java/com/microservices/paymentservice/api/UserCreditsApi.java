package com.microservices.paymentservice.api;

import com.microservices.paymentservice.model.UserCreditsView;
import com.microservices.paymentservice.service.UserCreditsViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/credits/user")
@RequiredArgsConstructor
public class UserCreditsApi {

    private final UserCreditsViewService service;

    @GetMapping("/{userId}")
    public Mono<UserCreditsView> getUserCredits(
            @PathVariable("userId") String userId
    ) {
        return service.getUserCredits(userId);
    }
}
