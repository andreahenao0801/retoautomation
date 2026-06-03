package com.challenge.runners;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.challenge.models.request.UserRequest;
import com.challenge.models.response.UserResponse;
import com.challenge.questions.TheResponseBody;
import com.challenge.questions.TheStatusCode;
import com.challenge.tasks.CreateUser;
import com.challenge.tasks.DeleteUser;
import com.challenge.tasks.GetUser;
import com.challenge.tasks.UpdateUser;
import com.challenge.utils.config.EnvironmentConfig;
import net.datafaker.Faker;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserCrudRunner {

  private final Actor actor = Actor.named("QA Specialist");
  private final Faker faker = new Faker();

  @BeforeAll
  void setUp() {
    String baseUrl = EnvironmentConfig.getBaseUrl();
    actor.can(CallAnApi.at(baseUrl));
  }

  @Test
  @Order(1)
  @DisplayName("Crear un usuario exitosamente (POST)")
  void shouldCreateUserSuccessfully() {
    UserRequest newUser =
        UserRequest.builder()
            .name(faker.name().fullName())
            .username(faker.internet().username())
            .email(faker.internet().emailAddress())
            .build();

    actor.attemptsTo(CreateUser.with(newUser));

    actor.should(
        seeThat(TheStatusCode.was(), equalTo(201)),
        seeThat(TheResponseBody.receivedWas().path("name"), equalTo(newUser.getName())),
        seeThat(TheResponseBody.receivedWas().path("username"), equalTo(newUser.getUsername())),
        seeThat(TheResponseBody.receivedWas().path("email"), equalTo(newUser.getEmail())),
        seeThat(TheResponseBody.receivedWas().path("id"), notNullValue()));

    // Obtener el ID y guardarlo en la sesión del actor (Manejo de sesión / Bonus)
    Integer id = actor.asksFor(TheResponseBody.as(UserResponse.class)).getId();
    actor.remember("userId", id);
  }

  @Test
  @Order(2)
  @DisplayName("Consultar un usuario por ID (GET)")
  void shouldGetUserSuccessfully() {
    // Consultamos un ID fijo de JSONPlaceholder ("2") para validar la respuesta
    // completa
    actor.attemptsTo(GetUser.withId(2));

    actor.should(
        seeThat(TheStatusCode.was(), equalTo(200)),
        seeThat(TheResponseBody.receivedWas().path("id"), equalTo(2)),
        seeThat(TheResponseBody.receivedWas().path("name"), equalTo("Ervin Howell")),
        seeThat(TheResponseBody.receivedWas().path("email"), equalTo("Shanna@melissa.tv")));
  }

  @Test
  @Order(3)
  @DisplayName("Actualizar un usuario (PUT)")
  void shouldUpdateUserSuccessfully() {
    // JSONPlaceholder no persiste los registros creados por POST en su base de
    // datos.
    // Actualizar el ID ficticio (ID 11) genera un error 500 en JSONPlaceholder.
    // Por ende, realizamos el PUT sobre el usuario existente con ID 2.
    Integer targetId = 2;

    UserRequest updatedUser =
        UserRequest.builder()
            .name(faker.name().fullName())
            .username(faker.internet().username())
            .email(faker.internet().emailAddress())
            .build();

    actor.attemptsTo(UpdateUser.with(targetId, updatedUser));

    actor.should(
        seeThat(TheStatusCode.was(), equalTo(200)),
        seeThat(TheResponseBody.receivedWas().path("name"), equalTo(updatedUser.getName())),
        seeThat(TheResponseBody.receivedWas().path("username"), equalTo(updatedUser.getUsername())),
        seeThat(TheResponseBody.receivedWas().path("email"), equalTo(updatedUser.getEmail())));
  }

  @Test
  @Order(4)
  @DisplayName("Eliminar un usuario (DELETE)")
  void shouldDeleteUserSuccessfully() {
    // Realizamos el DELETE sobre el usuario creado previamente.
    Integer targetId = actor.recall("userId");

    actor.attemptsTo(DeleteUser.withId(targetId));

    actor.should(
        seeThat(TheStatusCode.was(), equalTo(200)) // JSONPlaceholder devuelve 200 en DELETE
        );
  }

  @Test
  @Order(5)
  @DisplayName("Eliminar un usuario inexistente (DELETE)")
  void shouldNotDeleteNonExistentUser() {
    // Intentamos eliminar el usuario inexistente con ID 22
    Integer targetId = 22;

    actor.attemptsTo(DeleteUser.withId(targetId));

    actor.should(
        seeThat(
            TheStatusCode.was(), equalTo(200)) // JSONPlaceholder devuelve 200 al intentar eliminar
        // (idempotente), la idea era que devolviera 404 pero la API
        // no lo soporta, no importa lo que le envíes va a dar 200
        );
  }
}
