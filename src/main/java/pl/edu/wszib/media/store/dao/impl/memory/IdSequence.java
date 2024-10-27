package pl.edu.wszib.media.store.dao.impl.memory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class IdSequence {

    private int lastId = 0;

    public int getId() {
        return ++lastId;
    }
}
