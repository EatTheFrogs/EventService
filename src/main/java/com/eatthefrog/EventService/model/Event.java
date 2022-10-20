package com.eatthefrog.EventService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "events")
public class Event extends BaseModel {

    @Serial
    private static final long serialVersionUID = -3721461690283380472L;

    @Id
    private String id;
    private ZonedDateTime completedDate;
    @NotNull
    private String userUuid;
    private String name;
    @NotNull
    private String goalId;
}
