package com.eatthefrog.EventService.service;

import com.eatthefrog.EventService.client.GoalServiceClient;
import com.eatthefrog.EventService.controller.EventsController;
import com.eatthefrog.EventService.model.event.Event;
import com.eatthefrog.EventService.model.goal.Goal;
import com.eatthefrog.EventService.repository.EventRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;

@Log
@Service
@RequiredArgsConstructor
public class EventService {

    private final ObjectIdGenerator objectIdGenerator;
    private final EventRepo eventRepo;
    private final GoalServiceClient goalServiceClient;
    private final TransactionHandlerService transactionHandlerService;

    // Used in @Preauthorize annotation on controller
    public boolean assertUserOwnsEvent(String userUuid, String eventId) {
        Event event = getEventById(eventId);
        return StringUtils.equals(userUuid, event.getUserUuid());
    }

    public Collection<Event> getEventsForUser(String userUuid) {
        return eventRepo.findAllByUserUuid(userUuid);
    }

    public Event getEventById(String eventId) {
        return eventRepo.findById(eventId).orElseThrow(() -> new EventsController.ResourceNotFoundException("Couldn't find event with id "+eventId));
    }

    public Collection<Goal> createEvent(Event event) throws Exception {
        transactionHandlerService.runInTransaction(() -> createEventTransactional(event));
        return goalServiceClient.getAllGoals(event.getUserUuid());
    }

    public Collection<Goal> updateEvent(Event event) {
        getEventById(event.getId());
        initializeEmptyFieldIds(event);
        eventRepo.save(event);
        return goalServiceClient.getAllGoals(event.getUserUuid());
    }

    public Collection<Goal> deleteEvent(String eventId, String userUuid) throws Exception {
        transactionHandlerService.runInTransaction(() -> deleteEventTransactional(eventId));
        return goalServiceClient.getAllGoals(userUuid);
    }

    public void deleteEventsForGoalId(String goalId) {
        eventRepo.deleteByGoalId(goalId);
    }

    public void deleteAllEventsForUser(String userUuid) {
        eventRepo.deleteByUserUuid(userUuid);
    }

    private void createEventTransactional(Event event) {
        if(Objects.isNull(event.getCompletedDate())) {
            event.setCompletedDate(ZonedDateTime.now(ZoneId.of("UTC")));
        }
        initializeEmptyFieldIds(event);
        Event savedEvent = eventRepo.save(event);
        goalServiceClient.addEventToGoal(savedEvent);
    }

    public void deleteEventTransactional(String eventId) {
        Event event = getEventById(eventId);
        goalServiceClient.deleteEventFromGoal(event.getGoalId(), eventId);
        eventRepo.delete(event);
    }

    private Event initializeEmptyFieldIds(Event event) {
        event.getFields()
                .stream()
                .forEach(field -> {
                    if(Objects.isNull(field.getId())) {
                        field.setId(objectIdGenerator.generate().toString());
                    }
                });
        return event;
    }
}
