package com.eatthefrog.EventService.service;

import com.eatthefrog.EventService.client.GoalServiceClient;
import com.eatthefrog.EventService.controller.EventsController;
import com.eatthefrog.EventService.model.event.Event;
import com.eatthefrog.EventService.model.event.field.EventField;
import com.eatthefrog.EventService.model.goal.Goal;
import com.eatthefrog.EventService.repository.EventRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    public Collection<Goal> createFieldForEvent(EventField eventField, String eventId, String userUuid) {
        eventField.setId(objectIdGenerator.generate().toString());
        Event event = getEventById(eventId);
        ArrayList<EventField> fields = new ArrayList<EventField>(event.getFields().stream().toList());
        fields.add(eventField);
        event.setFields(fields);
        eventRepo.save(event);
        return goalServiceClient.getAllGoals(userUuid);
    }

    public Collection<Goal> updateEvent(Event event) {
        getEventById(event.getId());
        initializeEmptyFieldIds(event);
        eventRepo.save(event);
        return goalServiceClient.getAllGoals(event.getUserUuid());
    }

    public Collection<Goal> updateFieldForEvent(EventField eventField, String eventId, String userUuid) {
        Event event = getEventById(eventId);
        ArrayList<EventField> fields = new ArrayList<EventField>(event.getFields().stream().toList());
        int index = -1;
        for(int i=0; i<fields.size(); i++) {
            if(StringUtils.equals(fields.get(i).getId(), eventField.getId())) {
                index = i;
                break;
            }
        }
        if(index == -1) {
            throw new EventsController.ResourceNotFoundException(
                    String.format("Couldn't find EventField[%s] for Event[%s]", eventField.getId(), eventId));
        }
        fields.set(index, eventField);
        event.setFields(fields);
        eventRepo.save(event);
        return goalServiceClient.getAllGoals(userUuid);
    }

    public Collection<Goal> deleteEvent(String eventId, String userUuid) throws Exception {
        transactionHandlerService.runInTransaction(() -> deleteEventTransactional(eventId));
        return goalServiceClient.getAllGoals(userUuid);
    }

    public Collection<Goal> deleteFieldFromEvent(String eventId, String fieldId, String userUuid) throws Exception {
        Event event = getEventById(eventId);
        List fields = event.getFields().stream().filter(field -> !StringUtils.equals(field.getId(), fieldId)).toList();
        event.setFields(fields);
        eventRepo.save(event);
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
