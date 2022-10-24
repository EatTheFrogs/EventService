package com.eatthefrog.EventService.controller;

import com.eatthefrog.EventService.model.Event;
import com.eatthefrog.EventService.model.Goal;
import com.eatthefrog.EventService.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public static class OperationFailedException extends RuntimeException {
        public OperationFailedException(String message) {
            super(message);
        }
    }

    // Internal endpoints

    @PreAuthorize("hasAuthority('SCOPE_api')")
    @DeleteMapping("/delete/goal/{goalId}")
    public ResponseEntity deleteEventsForGoal(@PathVariable String goalId) {
        eventService.deleteEventsForGoalId(goalId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_api')")
    @DeleteMapping("/delete/user/{userUuid}")
    public ResponseEntity deleteAllEventsForUser(@PathVariable String userUuid) {
        eventService.deleteAllEventsForUser(userUuid);
        return ResponseEntity.ok().build();
    }

    // External endpoints

    @PreAuthorize("#userUuid == authentication.token.claims['uid']")
    @GetMapping("/{userUuid}")
    public Collection<Event> getEventsForUser(@PathVariable String userUuid) {
        return eventService.getEventsForUser(userUuid);
    }

    @PreAuthorize("#event.getUserUuid() == authentication.token.claims['uid']")
    @PostMapping("/create")
    public Collection<Goal> createEvent(@RequestBody Event event) throws Exception {
        return eventService.createEvent(event);
    }

    @PreAuthorize("#event.getUserUuid() == authentication.token.claims['uid']")
    @PatchMapping("/update")
    public Collection<Goal> updateEvent(@RequestBody Event event) {
        return eventService.updateEvent(event);
    }

    @PreAuthorize("#event.getUserUuid() == authentication.token.claims['uid']")
    @DeleteMapping("/delete")
    public Collection<Goal> deleteEvent(@RequestBody Event event) throws Exception {
        return eventService.deleteEvent(event);
    }
}
