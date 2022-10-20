package com.eatthefrog.EventService.service;

import com.eatthefrog.EventService.client.GoalServiceClient;
import com.eatthefrog.EventService.model.Event;
import com.eatthefrog.EventService.model.Goal;
import com.eatthefrog.EventService.repository.EventRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;

@Log
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final GoalServiceClient goalServiceClient;

    public Collection<Event> getEventsForUser(String userUuid) {
        return eventRepo.findAllByUserUuid(userUuid);
    }

    public Collection<Goal> createEvent(Event event) {
        createEventTransactional(event);
        return goalServiceClient.getAllGoals(event.getUserUuid());
    }

    public Collection<Goal> updateEvent(Event event) {
        eventRepo.save(event);
        return goalServiceClient.getAllGoals(event.getUserUuid());
    }

    public Collection<Goal> deleteEvent(Event event) {
        deleteEventTransactional(event);
        return goalServiceClient.getAllGoals(event.getUserUuid());
    }

    public void deleteEventsForGoalId(String goalId) {
        eventRepo.deleteByGoalId(goalId);
    }

    public void deleteAllEventsForUser(String userUuid) {
        eventRepo.deleteByUserUuid(userUuid);
    }

    @Transactional(rollbackFor=Exception.class)
    private void createEventTransactional(Event event) {
        try {
            event.setCompletedDate(ZonedDateTime.now(ZoneId.of("UTC")));
            Event savedEvent = eventRepo.save(event);
            goalServiceClient.addEventToGoal(savedEvent);
        } catch(Exception e) {
            log.severe(ExceptionUtils.getStackTrace(e));
            throw e;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteEventTransactional(Event event) {
        try {
            goalServiceClient.deleteEventFromGoal(event.getGoalId(), event.getId());
            eventRepo.delete(event);
        } catch(Exception e) {
            log.severe(ExceptionUtils.getStackTrace(e));
            throw e;
        }
    }
}
