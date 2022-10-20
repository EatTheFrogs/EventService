package com.eatthefrog.EventService.service;

import com.eatthefrog.EventService.client.GoalServiceClient;
import com.eatthefrog.EventService.model.Event;
import com.eatthefrog.EventService.model.Goal;
import com.eatthefrog.EventService.repository.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;

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

    @Transactional
    private void createEventTransactional(Event event) {
        event.setCompletedDate(ZonedDateTime.now(ZoneId.of("UTC")));
        Event savedEvent = eventRepo.save(event);
        goalServiceClient.addEventToGoal(savedEvent);
    }

    @Transactional
    public void deleteEventTransactional(Event event) {
        goalServiceClient.deleteEventFromGoal(event);
        eventRepo.delete(event);
    }
}
