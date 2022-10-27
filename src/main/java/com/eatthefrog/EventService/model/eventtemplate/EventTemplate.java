package com.eatthefrog.EventService.model.eventtemplate;

import com.eatthefrog.EventService.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serial;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include=JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes( {@JsonSubTypes.Type(value = DefaultEventTemplate.class, name = "default"), @JsonSubTypes.Type(value = LiftEventTemplate.class, name = "lift")} )
public class EventTemplate extends BaseModel {

    @Serial
    private static final long serialVersionUID = -8880429014095596424L;

    @Id
    private String id;
    private String type;
    private String userUuid;
    private String goalId;
    private String name;
}
