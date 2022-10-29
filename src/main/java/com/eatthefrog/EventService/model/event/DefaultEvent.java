package com.eatthefrog.EventService.model.event;

import com.eatthefrog.EventService.model.event.field.DefaultEventField;
import com.eatthefrog.EventService.model.event.field.EventField;
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
@TypeAlias("event.default")
@Document(collection = "events")
public class DefaultEvent extends Event {

    @Serial
    private static final long serialVersionUID = -1908016573732803067L;

    private List<DefaultEventField> fields = new ArrayList<DefaultEventField>();

    @Override
    public Collection<DefaultEventField> getFields() {
        return fields;
    }

    @Override
    public void setFields(Collection<EventField> fields) {
        this.fields = fields.stream()
                .map(DefaultEventField.class::cast)
                .toList();
    }
}