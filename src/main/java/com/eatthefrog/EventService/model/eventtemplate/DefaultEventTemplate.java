package com.eatthefrog.EventService.model.eventtemplate;

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
@TypeAlias("eventtemplate.default")
@Document(collection = "eventtemplates")
public class DefaultEventTemplate extends EventTemplate {

    @Serial
    private static final long serialVersionUID = -8880429014095596424L;

    private Collection<DefaultEventTemplateField> fields = new ArrayList<DefaultEventTemplateField>();
}
