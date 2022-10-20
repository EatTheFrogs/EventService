package com.eatthefrog.EventService.model;

import lombok.Data;

import java.io.Serial;
import java.util.Collection;

@Data
public class EventTemplate extends BaseModel {

    @Serial
    private static final long serialVersionUID = -8880429014095596424L;

    private Collection<EventTemplateField> fields;
}
