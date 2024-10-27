package pl.edu.wszib.media.store.session;

import lombok.Getter;
import lombok.Setter;
import pl.edu.wszib.media.store.model.User;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Getter
@Setter
public class SessionObject {
    User user;
    String info;
    int cos;
}
