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
@TypeAlias("event.field.lift.set")
public class LiftSet extends BaseModel {

    @Serial
    private static final long serialVersionUID = 8299817895886498174L;

    private int reps;
    private int weight;
}
