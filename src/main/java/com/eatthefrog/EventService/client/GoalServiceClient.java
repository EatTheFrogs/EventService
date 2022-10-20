package com.eatthefrog.EventService.client;

import com.eatthefrog.EventService.controller.EventsController;
import com.eatthefrog.EventService.model.Event;
import com.eatthefrog.EventService.model.Goal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoalServiceClient {

    private static final String GET_PATH = "/{goalId}";
    private static final String CREATE_PATH = "/create/event";
    private static final String DELETE_PATH = "/delete/event";

    private final WebClient goalServiceWebClient;

    public Collection<Goal> getAllGoals(String userUuid) {
        return goalServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(GET_PATH)
                        .build(userUuid))
                .retrieve()
                .bodyToFlux(Goal.class)
                .collect(Collectors.toList())
                .block();
    }

    public void addEventToGoal(Event event) {
        goalServiceWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(CREATE_PATH)
                        .build())
                .body(Mono.just(event), Event.class)
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        response -> response.bodyToMono(String.class).map(EventsController.OperationFailedException::new))
                .bodyToMono(Object.class)
                .block();
    }

    public void deleteEventFromGoal(Event event) {
        goalServiceWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(DELETE_PATH)
                        .build())
                .body(Mono.just(event), Event.class)
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        response -> response.bodyToMono(String.class).map(EventsController.OperationFailedException::new))
                .bodyToMono(Object.class)
                .block();
    }
}
