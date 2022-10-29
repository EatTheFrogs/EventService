package com.eatthefrog.EventService.model.event;

import com.eatthefrog.EventService.model.event.field.EventField;
import com.eatthefrog.EventService.model.event.field.LiftEventField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("event.lift")
@Document(collection = "events")
public class LiftEvent extends Event {

    @Serial
    private static final long serialVersionUID = 304787125395169578L;

    private List<LiftEventField> fields = new ArrayList<LiftEventField>();

    @Override
    public Collection<LiftEventField> getFields() {
        return fields;
    }

    @Override
    public void setFields(Collection<EventField> fields) {
        this.fields = fields.stream()
                .map(LiftEventField.class::cast)
                .toList();
    }
}