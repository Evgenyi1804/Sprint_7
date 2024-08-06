package org.example;

import io.restassured.response.Response;
import org.apache.http.entity.ContentType;

import static io.restassured.RestAssured.given;

public class OrderStep {


    public Response createOrder(String[] color) {
        Order order = new Order(color);
        return given()
                .contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(order)
                .when()
                .post("/api/v1/orders");
    }
}