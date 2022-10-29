package com.eatthefrog.EventService.model.event.field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("event.field.lift")
public class LiftEventField extends EventField {

    @Serial
    private static final long serialVersionUID = -2369904472045495167L;

    private List<LiftSet> sets = new ArrayList<LiftSet>();
}