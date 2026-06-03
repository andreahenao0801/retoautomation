package com.challenge.questions;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class TheResponseBody {

    public static Question<Response> received() {
        return actor -> SerenityRest.lastResponse();
    }

    public static <T> Question<T> as(Class<T> clazz) {
        return actor -> SerenityRest.lastResponse().as(clazz);
    }

    public static TheResponseBodyQuery receivedWas() {
        return new TheResponseBodyQuery();
    }

    public static class TheResponseBodyQuery {
        public Question<Object> path(String path) {
            return actor -> SerenityRest.lastResponse().path(path);
        }
    }
}
