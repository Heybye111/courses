package x_clients;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import netscape.javascript.JSObject;
import x_clients.model.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class XClientsContractTests {
    public static final String URL = "https://x-clients-be.onrender.com/company";
    public static final String URL_ID = "https://x-clients-be.onrender.com/company/{id}";
    public static final String URL_AUTH = "https://x-clients-be.onrender.com/auth/login";

    public static final String employeeURL = "https://x-clients-be.onrender.com/employee";

    public static String TOKEN;

 //   public int companyId;


    @BeforeAll

    static void auth() {
        getToken();
//        int companyId = createNewCompany();
    }




    @Test
    void createNewEmployee() {
        int companyId = 1504;
        String employeeParams = """
                {
                 "firstName": "Andrew",
                 "lastName": "Antonov",
                 "middleName": "Olegovich",
                 "companyId":""" + companyId + """
                 , "email": "ya@mail.ru",
                 "url": "asdsa",
                 "phone": "21321312",
                 "birthdate": "2024-04-14",
                 "isActive": true
                 }
                """;
        int newEmployeeId = createNewEmployee(companyId,employeeParams);
        assertNotNull(newEmployeeId);
        assertNotEquals(newEmployeeId, 0);
    }


    @Test
    void getEmployeeInfo() {
        int companyId = 1504;
        String employeeParams = """
                {
                 "firstName": "Andrew",
                 "lastName": "Antonov",
                 "middleName": "Olegovich",
                 "companyId":""" + companyId + """
                 , "email": "ya@mail.ru",
                 "url": "asdsa",
                 "phone": "21321312",
                 "birthdate": "2024-04-14",
                 "isActive": true
                 }
                """;
        int employeeId = createNewEmployee(companyId,employeeParams);
        Employee employee = getEmployeeInfo(employeeId);

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(employeeParams, JsonObject.class);
        assertEquals(employee.getFirstName(), jsonObj.get("firstName").getAsString());
        assertTrue(employee.getActive(),jsonObj.get("isActive").getAsString());
        assertEquals(employee.getPhone(), jsonObj.get("phone").getAsString());
        assertEquals(employee.getBirthdate(),jsonObj.get("birthdate").getAsString());
        assertEquals(employee.getAvatar_url(), jsonObj.get("url").getAsString());
        assertEquals(employee.getEmail(), jsonObj.get("email").getAsString());
        assertEquals(employee.getLastName(), jsonObj.get("lastName").getAsString());
        assertEquals(employee.getMiddleName(), jsonObj.get("Olegovich").getAsString());
    }


    @Test
    public void patchEmployee() {
        Employee employee = patchEmployeeInfo(4844);
        System.out.println(employee);


    }

//    @Test
//    void createEmployeeAndAsserWithDataBase() {
//        int companyId = createNewCompany();
//        assertNotNull(companyId);
//        int employeeId = createNewEmployee(companyId);
//        assertNotNull(employeeId);
//
//
//    }

    public static String getToken() {
        String creds = """
                {
                "username": "bloom",
                "password": "fire-fairy"
                }
                        """;
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


    public Employee patchEmployeeInfo(int employeeId) {
        String employeeParams = """
                {
                "lastName": "NotAndrew",
                  "email": "google@ya.ru",
                  "url": "Changed",
                  "phone": "99005553535",
                  "isActive": false
                }
                """;

        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("x-client-token", TOKEN)
                .body(employeeParams)
                .when()
                .patch(employeeURL + "/" + employeeId)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(Employee.class);
    }

    public static int createNewCompany() {
        String myCompanyName = """ 
                {
                  "name": "Beta bank"
                }
                """;
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

    public int createNewEmployee(int companyId, String employeeParams) {

        return given()
                .log().all()
                .body(employeeParams)
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

    public Employee getEmployeeInfo(int employeeId) {
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

}


