package org.acme;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.mapper.factory.DefaultJackson2ObjectMapperFactory;
import org.acme.config.MyObjectMapperCustomizer;
import org.acme.dto.CredentialsDTO;
import org.acme.dto.MyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Inject
    MyObjectMapperCustomizer mapCust;

    @BeforeEach
    void init() {
        RestAssured.config = RestAssuredConfig.newConfig().objectMapperConfig(
                ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(new DefaultJackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Type type, String s) {
                        ObjectMapper objectMapper = super.create(type, s);
                        mapCust.customize(objectMapper);
                        return objectMapper;
                    }
                })
        );
    }


    @Test
    @DisplayName("happy path creation")
    void successCreate() {
        var cred = new CredentialsDTO();
        cred.setLogin("blabla");
        cred.setPassword("123456");

        var dto = new MyDTO();
        dto.setId("123");
        dto.setName("12355");
        dto.setAge(25);
        dto.setCredentials(cred);

        given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(dto)
                .post("/hello")
                .then().log().all()
                .assertThat()
                .statusCode(204);
    }

    @Test
    @DisplayName("fail to create because id and credentials do not exist")
    void failCreat() {
        var dto = new MyDTO();
        dto.setName("12355");
        dto.setAge(25);
        given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(dto)
                .post("/hello")
                .then().log().all()
                .assertThat()
                .statusCode(409);
    }

    @Test
    @DisplayName("minimal update")
    void successUpdate() {
        var dto = new MyDTO();
        dto.setName("12345");
        dto.setAge(25);
        given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(dto)
                .put("/hello/abcdef")
                .then().log().all()
                .assertThat()
                .statusCode(204);
    }

    @Test
    @DisplayName("fail to update because id is set")
    void failUpdateBecauseOfId() {
        var dto = new MyDTO();
        dto.setName("xyz");
        dto.setId("123456");
        dto.setAge(25);
        given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(dto)
                .put("/hello/abcdef")
                .then().log().all()
                .assertThat()
                .statusCode(409);
    }

}