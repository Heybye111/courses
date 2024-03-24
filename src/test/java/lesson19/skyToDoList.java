package lesson19;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class skyToDoList {

    public static final String URL = "https://sky-todo-list.herokuapp.com";


    @Test
    public void createNewTask(){

        String Json = """
                {
                    "title" : "Sleep"
                    "completed" : false
                }
                """;

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .accept("application/json")

                .body(Json)
                .post(URL)
                .then()
                .log().all()
                .statusCode(201)
                .body("title", equalTo("Sleep"));

    }

    @Test
    void getToDoList(){

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .get(URL)
                .then()
                .log().all()
                .statusCode(200);
    }





}
