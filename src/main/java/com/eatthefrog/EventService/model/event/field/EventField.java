package com.eatthefrog.EventService.model.event.field;

import com.eatthefrog.EventService.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serial;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes( {@JsonSubTypes.Type(value = DefaultEventField.class, name = "default"), @JsonSubTypes.Type(value = LiftEventField.class, name = "lift")} )
public abstract class EventField extends BaseModel {

    @Serial
    private static final long serialVersionUID = 3290966599970807684L;

    @Id
    private String id;
    private String name;
}
