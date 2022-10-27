package com.eatthefrog.EventService.model.event;

import com.eatthefrog.EventService.model.BaseModel;
import lombok.Data;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Data
public class Lift extends BaseModel {

    @Serial
    private static final long serialVersionUID = -2369904472045495167L;

    private String name;
    private List<LiftSet> sets = new ArrayList<LiftSet>();
}