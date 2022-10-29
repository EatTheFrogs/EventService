package com.eatthefrog.EventService.repository;

import com.eatthefrog.EventService.model.event.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface EventRepo extends MongoRepository<Event, String> {

    public Optional<Event> findById(String eventId);

    public Collection<Event> findAllByUserUuid(String userUuid);

    public Collection<Event> findAllByGoalId(String goalId);

    public Collection<Event> findAllByUserUuidOrderByCompletedDateDesc(String userUuid);

    public void deleteByGoalId(String goalId);

    public void deleteByUserUuid(String userUuid);
}
