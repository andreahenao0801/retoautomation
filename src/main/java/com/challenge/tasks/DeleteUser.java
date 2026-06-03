package com.challenge.tasks;

import com.challenge.interactions.api.Delete;
import com.challenge.utils.constants.Resources;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeleteUser implements Task {
    private final String id;

    public DeleteUser(Object id) {
        this.id = String.valueOf(id);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Delete.from(Resources.USERS.getValue() + "/" + id)
        );
    }

    public static DeleteUser withId(Object id) {
        return instrumented(DeleteUser.class, id);
    }
}
