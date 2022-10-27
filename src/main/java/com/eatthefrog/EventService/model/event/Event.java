package com.eatthefrog.EventService.model.event;

import com.eatthefrog.EventService.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include=JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes( {@JsonSubTypes.Type(value = DefaultEvent.class, name = "default"), @JsonSubTypes.Type(value = LiftEvent.class, name = "lift")} )
@Document(collection = "events")
public class Event extends BaseModel {

    @Serial
    private static final long serialVersionUID = -3721461690283380472L;

    @Id
    private String id;
    private String type;
    private ZonedDateTime completedDate;
    @NotNull
    private String userUuid;
    private String name;
    @NotNull
    private String goalId;
}
