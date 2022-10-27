package com.eatthefrog.EventService.model.eventtemplate;

import com.eatthefrog.EventService.model.BaseModel;
import lombok.Data;

import java.io.Serial;

@Data
public class DefaultEventTemplateField extends BaseModel {

    @Serial
    private static final long serialVersionUID = 4950041889292890771L;

    private String name;
    private String type;
    private String unit;
}
