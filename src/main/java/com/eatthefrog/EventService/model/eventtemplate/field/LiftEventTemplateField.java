package com.eatthefrog.EventService.model.eventtemplate.field;

import com.eatthefrog.EventService.model.event.field.LiftSet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("eventtemplate.field.lift")
public class LiftEventTemplateField extends EventTemplateField {

    @Serial
    private static final long serialVersionUID = 2915210869392065675L;

    private Collection<LiftSet> sets = new ArrayList<LiftSet>();
}