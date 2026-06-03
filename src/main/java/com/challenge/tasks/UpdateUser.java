package com.challenge.tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import com.challenge.interactions.api.Put;
import com.challenge.models.request.UserRequest;
import com.challenge.utils.constants.Resources;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class UpdateUser implements Task {
  private final String id;
  private final UserRequest payload;

  public UpdateUser(Object id, UserRequest payload) {
    this.id = String.valueOf(id);
    this.payload = payload;
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        Put.to(Resources.USERS.getValue() + "/" + id)
            .with(request -> request.contentType(ContentType.JSON).body(payload)));
  }

  public static UpdateUser with(Object id, UserRequest payload) {
    return instrumented(UpdateUser.class, id, payload);
  }
}
