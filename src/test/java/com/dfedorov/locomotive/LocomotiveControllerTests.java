/*
 * Locomotive Numbers Validator
 * Copyright (c) 2019.
 */

package com.dfedorov.locomotive;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocomotiveControllerTests {

    @LocalServerPort
    private int port;

    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);

    @Test
    public void testIndex() {
        given().port(port).get("/").then().assertThat()
                .body(containsString("Use the following format"));
        given().port(port).get("/").then().assertThat()
                .statusCode(200);
    }

    @Test
    public void testApiBadRequest() {
        given().port(port).get("/api").then().assertThat()
                .body(containsString("Bad Request"));
        given().port(port).get("/api").then().assertThat()
                .statusCode(400);

        given().port(port).get("/api?locomotive=123456789").
                then().assertThat().body(containsString("Bad Request"));
        given().port(port).get("/api?locomotive=123456789").
                then().assertThat().statusCode(400);

        given().port(port).get("/api?locomotive=123456-8").
                then().assertThat().body(containsString("Bad Request"));
        given().port(port).get("/api?locomotive=123456-8").
                then().assertThat().statusCode(400);
    }

    @Test
    public void testApiGoodRequest() {
        given().port(port).
                param("locomotive", "10140051").
                when().
                get("/api").
                then().body("reihenNumber", equalTo(1014)).
                and().body("ordnungsNumber", equalTo(5)).
                and().body("checkDigit", equalTo(1)).
                and().body("isValid", equalTo(true));

        given().port(port).
                param("locomotive", "11160645").
                when().
                get("/api").
                then().body("reihenNumber", equalTo(1116)).
                and().body("ordnungsNumber", equalTo(64)).
                and().body("checkDigit", equalTo(5)).
                and().body("isValid", equalTo(true));
        given().port(port).
                param("locomotive", "11426061").
                when().
                get("/api").
                then().body("reihenNumber", equalTo(1142)).
                and().body("ordnungsNumber", equalTo(606)).
                and().body("checkDigit", equalTo(1)).
                and().body("isValid", equalTo(true));

        given().port(port).
                param("locomotive", "10140053").
                when().
                get("/api").
                then().body("reihenNumber", equalTo(1014)).
                and().body("ordnungsNumber", equalTo(5)).
                and().body("checkDigit", equalTo(3)).
                and().body("isValid", equalTo(false));

        given().port(port).
                param("locomotive", "11426068").
                when().
                get("/api").
                then().body("reihenNumber", equalTo(1142)).
                and().body("ordnungsNumber", equalTo(606)).
                and().body("checkDigit", equalTo(8)).
                and().body("isValid", equalTo(false));
    }
}
