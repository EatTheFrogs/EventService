package com.eatthefrog.EventService.model.event.field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.io.Serial;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("event.field.default")
public class DefaultEventField extends EventField {

    @Serial
    private static final long serialVersionUID = -6890295343670089002L;

    private String type;
    private String unit;
    private String value;
}
