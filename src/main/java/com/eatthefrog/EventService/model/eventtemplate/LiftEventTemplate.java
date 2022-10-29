package com.eatthefrog.EventService.model.eventtemplate;

import com.eatthefrog.EventService.model.eventtemplate.field.EventTemplateField;
import com.eatthefrog.EventService.model.eventtemplate.field.LiftEventTemplateField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("eventtemplate.lift")
@Document(collection = "eventtemplates")
public class LiftEventTemplate extends EventTemplate {

    @Serial
    private static final long serialVersionUID = -8880429014095596424L;

    private Collection<LiftEventTemplateField> fields = new ArrayList<LiftEventTemplateField>();

    @Override
    public Collection<LiftEventTemplateField> getFields() {
        return fields;
    }

    @Override
    public void setFields(Collection<EventTemplateField> fields) {
        this.fields = fields.stream()
                .map(LiftEventTemplateField.class::cast)
                .toList();
    }
}
