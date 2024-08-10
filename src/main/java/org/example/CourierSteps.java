package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

public class CourierSteps {
    @Step("Создание курьера")
    public Response createCourier(String login, String password, String firstName) {
        return given()
                .contentType(ContentType.JSON)
                    .baseUri("https://qa-scooter.praktikum-services.ru")
                .body("{\n" +
                        "    \"login\": \"" + login + "\",\n" +
                        "    \"password\": \"" + password + "\",\n" +
                        "    \"firstName\": \"" + firstName + "\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier");
    }
    @Step("Регистрация курьера")
    public ValidatableResponse loginCourier(String login, String password) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body("{\n" +
                        "    \"login\": \"" + login + "\",\n" +
                        "    \"password\": \"" + password + "\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier/login")
                .then();
    }
    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(String id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/:id" + "{id}")
                .then();
    }
}