package com.challenge.tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import com.challenge.interactions.api.Get;
import com.challenge.utils.constants.Resources;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class GetUser implements Task {
  private final String id;

  public GetUser(Object id) {
    this.id = String.valueOf(id);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(Get.resource(Resources.USERS.getValue() + "/" + id));
  }

  public static GetUser withId(Object id) {
    return instrumented(GetUser.class, id);
  }
}
