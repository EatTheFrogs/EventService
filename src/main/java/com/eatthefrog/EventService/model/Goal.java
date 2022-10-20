package com.eatthefrog.EventService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal extends BaseModel {

    @Serial
    private static final long serialVersionUID = -8357126405162601677L;

    @Id
    private String id;
    private ZonedDateTime createdDate;
    @NotNull
    private String userUuid;
    private String name;
    private String description;
    private List<Event> completedEvents = new ArrayList<Event>();
    private List<EventTemplate> eventTemplates = new ArrayList<EventTemplate>();
}
