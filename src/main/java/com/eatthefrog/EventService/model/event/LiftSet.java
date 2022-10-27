package com.eatthefrog.EventService.model.event;

import com.eatthefrog.EventService.model.BaseModel;
import lombok.Data;

import java.io.Serial;

@Data
public class LiftSet extends BaseModel {

    @Serial
    private static final long serialVersionUID = 8299817895886498174L;

    private int reps;
    private int weight;
}
