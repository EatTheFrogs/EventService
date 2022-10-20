package com.eatthefrog.EventService.model;

import lombok.Data;

import java.io.Serial;

@Data
public class EventTemplateField extends BaseModel {

    @Serial
    private static final long serialVersionUID = 4950041889292890771L;

    private String name;
    private String type;
    private String unit;
}
