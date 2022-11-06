package com.eatthefrog.EventService.controller;

import com.eatthefrog.EventService.model.event.Event;
import com.eatthefrog.EventService.model.event.field.EventField;
import com.eatthefrog.EventService.model.goal.Goal;
import com.eatthefrog.EventService.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

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

    @PreAuthorize("@eventService.assertUserOwnsEvent(#jwt.getClaim('uid').toString(), #eventId)")
    @PostMapping("/create/{eventId}/field")
    public Collection<Goal> createFieldForEvent(@AuthenticationPrincipal Jwt jwt, @PathVariable String eventId, @RequestBody EventField eventField) throws Exception {
        return eventService.createFieldForEvent(eventField, eventId, jwt.getClaim("uid").toString());
    }

    @PreAuthorize("#event.getUserUuid() == authentication.token.claims['uid']")
    @PatchMapping("/update")
    public Collection<Goal> updateEvent(@RequestBody Event event) {
        return eventService.updateEvent(event);
    }

    @PreAuthorize("@eventService.assertUserOwnsEvent(#jwt.getClaim('uid').toString(), #eventId)")
    @PatchMapping("/update/{eventId}/field")
    public Collection<Goal> updateFieldForEvent(@AuthenticationPrincipal Jwt jwt, @PathVariable String eventId, @RequestBody EventField eventField) {
        return eventService.updateFieldForEvent(eventField, eventId, jwt.getClaim("uid").toString());
    }

    @PreAuthorize("@eventService.assertUserOwnsEvent(#jwt.getClaim('uid').toString(), #eventId)")
    @DeleteMapping("/delete/{eventId}")
    public Collection<Goal> deleteEvent(@AuthenticationPrincipal Jwt jwt, @PathVariable String eventId) throws Exception {
        return eventService.deleteEvent(eventId, jwt.getClaim("uid").toString());
    }

    @PreAuthorize("@eventService.assertUserOwnsEvent(#jwt.getClaim('uid').toString(), #eventId)")
    @DeleteMapping("/delete/{eventId}/field/{fieldId}")
    public Collection<Goal> deleteFieldFromEvent(@AuthenticationPrincipal Jwt jwt, @PathVariable String eventId, @PathVariable String fieldId) throws Exception {
        return eventService.deleteFieldFromEvent(eventId, fieldId, jwt.getClaim("uid").toString());
    }
}
