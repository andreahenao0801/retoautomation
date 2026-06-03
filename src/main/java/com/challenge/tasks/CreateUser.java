package com.challenge.tasks;

import com.challenge.interactions.api.Post;
import com.challenge.models.request.UserRequest;
import com.challenge.utils.constants.Resources;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreateUser implements Task {
    private final UserRequest payload;

    public CreateUser(UserRequest payload) {
        this.payload = payload;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Post.to(Resources.USERS.getValue())
                .with(request -> request
                    .contentType(ContentType.JSON)
                    .body(payload))
        );
    }

    public static CreateUser with(UserRequest payload) {
        return instrumented(CreateUser.class, payload);
    }
}
