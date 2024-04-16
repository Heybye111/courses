package ru.inno.courses.certification;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.inno.courses.certification.model.Employee;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

public class XClients {
    public static final String URL = "https://x-clients-be.onrender.com/company";
    public static final String URL_AUTH = "https://x-clients-be.onrender.com/auth/login";
    public static final String employeeURL = "https://x-clients-be.onrender.com/employee";
    public static String TOKEN;


    public static String getToken(String username, String password) {
        String creds = "{\"username\": \"" + username + "\",\"password\": \"" + password + "\"}";
        TOKEN = given()
                .log().all()
                .body(creds)
                .contentType(ContentType.JSON)
                .when().post(URL_AUTH)
                .then().log().all()
                .statusCode(201)
                .extract().path("userToken");

        return TOKEN;
    }

    public static Employee patchEmployeeInfo(int employeeId, String patchEmployeeParams) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("x-client-token", TOKEN)
                .body(patchEmployeeParams)
                .when()
                .patch(employeeURL + "/" + employeeId)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(Employee.class);
    }

    public static int createNewCompany(String myCompanyName) {
        return given()
                .log().all()
                .body(myCompanyName)
                .header("x-client-token", TOKEN)
                .contentType(ContentType.JSON)
                .when().post(URL)
                .then()
                .log().all()
                .statusCode(201)
                .body("id", greaterThan(0))
                .extract().path("id");
    }

    public static int createNewEmployee(Employee employee) {
        return given()
                .log().all()
                .body(employee)
                .header("x-client-token", TOKEN)
                .contentType(ContentType.JSON)
                .when().post(employeeURL)
                .then()
                .log().all()
                .statusCode(201)
                .contentType("application/json; charset=utf-8")
                .body("id", greaterThan(1))
                .extract().path("id");
    }


    public static Employee getEmployeeInfo(int employeeId) {
        Response response = RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(employeeURL + "/" + employeeId)
                .then()
                .log().all()
                .statusCode(200) //В свагере описано что статус код должен быть 201, по факту 200. так что тут баг в контракте
                .extract().response();
        return response.body().as(Employee.class);
    }

    public static List<Employee> getListOfEmployees(int companyId) {
        Response response = RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(employeeURL + "?company=" + companyId)
                .then()
                .assertThat().statusCode(200)
                .extract().response();

        return response.body().jsonPath()
                .getList(".", Employee.class);

    }

    public static void main(String[] args) {
    }
}
