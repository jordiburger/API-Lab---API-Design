package de.berlin.htw;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class ProtectedResourceTest {

    @Test
    void testProtectedEndpoint() {
        given()
          .when().get("/protected")
          .then()
             .statusCode(401)
             .header("X-My-Header", "Filter was executed");
    }

}