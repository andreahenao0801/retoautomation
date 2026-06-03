package com.challenge.interactions.api;

import static net.serenitybdd.screenplay.rest.abilities.CallAnApi.as;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;

public class Put extends RestInteraction {
  private final String resource;

  public Put(String resource) {
    this.resource = resource;
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    rest().put(as(actor).resolve(resource));
  }

  public static Put resource(String resource) {
    return new Put(resource);
  }

  public static Put to(String resource) {
    return new Put(resource);
  }
}
