package com.eatthefrog.EventService.service;

import com.eatthefrog.EventService.client.GoalServiceClient;
import com.eatthefrog.EventService.model.event.Event;
import com.eatthefrog.EventService.model.goal.Goal;
import com.eatthefrog.EventService.repository.EventRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;

@Log
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final GoalServiceClient goalServiceClient;
    private final TransactionHandlerService transactionHandlerService;

    public Collection<Event> getEventsForUser(String userUuid) {
        return eventRepo.findAllByUserUuid(userUuid);
    }

    public Collection<Goal> createEvent(Event event) throws Exception {
        transactionHandlerService.runInTransaction(() -> createEventTransactional(event));
        return goalServiceClient.getAllGoals(event.getUserUuid());
    }

    public Collection<Goal> updateEvent(Event event) {
        eventRepo.save(event);
        return goalServiceClient.getAllGoals(event.getUserUuid());
    }

    public Collection<Goal> deleteEvent(Event event) throws Exception {
        transactionHandlerService.runInTransaction(() -> deleteEventTransactional(event));
        return goalServiceClient.getAllGoals(event.getUserUuid());
    }

    public void deleteEventsForGoalId(String goalId) {
        eventRepo.deleteByGoalId(goalId);
    }

    public void deleteAllEventsForUser(String userUuid) {
        eventRepo.deleteByUserUuid(userUuid);
    }

    private void createEventTransactional(Event event) {
        event.setCompletedDate(ZonedDateTime.now(ZoneId.of("UTC")));
        Event savedEvent = eventRepo.save(event);
        goalServiceClient.addEventToGoal(savedEvent);
    }

    public void deleteEventTransactional(Event event) {
        goalServiceClient.deleteEventFromGoal(event.getGoalId(), event.getId());
        eventRepo.delete(event);
    }
}
