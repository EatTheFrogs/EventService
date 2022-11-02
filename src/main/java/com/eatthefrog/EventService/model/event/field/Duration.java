package com.eatthefrog.EventService.model.event.field;

import com.eatthefrog.EventService.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.io.Serial;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("event.field.duration")
public class Duration extends BaseModel {

    @Serial
    private static final long serialVersionUID = 2464079261707018599L;

    private int hours;
    private int minutes;
    private int seconds;
}
