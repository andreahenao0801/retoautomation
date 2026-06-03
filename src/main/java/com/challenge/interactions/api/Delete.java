package com.challenge.interactions.api;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;
import static net.serenitybdd.screenplay.rest.abilities.CallAnApi.as;

public class Delete extends RestInteraction {
    private final String resource;

    public Delete(String resource) {
        this.resource = resource;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        rest().delete(as(actor).resolve(resource));
    }

    public static Delete resource(String resource) {
        return new Delete(resource);
    }

    public static Delete from(String resource) {
        return new Delete(resource);
    }

    public static Delete to(String resource) {
        return new Delete(resource);
    }
}
