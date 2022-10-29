package com.eatthefrog.EventService.model.eventtemplate;

import com.eatthefrog.EventService.model.BaseModel;
import com.eatthefrog.EventService.model.eventtemplate.field.EventTemplateField;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.util.Collection;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include=JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes( {@JsonSubTypes.Type(value = DefaultEventTemplate.class, name = "default"), @JsonSubTypes.Type(value = LiftEventTemplate.class, name = "lift")} )
@Document(collection = "eventtemplates")
public abstract class EventTemplate extends BaseModel {

    @Serial
    private static final long serialVersionUID = -8880429014095596424L;

    @Id
    private String id;
    private String type;
    private String userUuid;
    private String goalId;
    private String name;

    public abstract Collection<? extends EventTemplateField> getFields();
    public abstract void setFields(Collection<EventTemplateField> fields);
}
