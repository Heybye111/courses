package x_clients;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import x_clients.model.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class XClientsContractTests {
    public static final String URL = "https://x-clients-be.onrender.com/company";
    public static final String URL_ID = "https://x-clients-be.onrender.com/company/{id}";
    public static final String URL_AUTH = "https://x-clients-be.onrender.com/auth/login";

    public static final String employeeURL = "https://x-clients-be.onrender.com/employee";

    public static String TOKEN;

    static int companyId;


    @BeforeAll

    public static void setup() throws IOException {
        getToken();
        String myCompanyName = """ 
                {
                  "name": "Beta bank"
                }
                """;
        companyId = createNewCompany(myCompanyName);
    }

    @Test
    void createNewEmployee() {
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
        int newEmployeeId = createNewEmployee(employeeParams);
        assertNotNull(newEmployeeId);
        assertNotEquals(newEmployeeId, 0);
    }

    @Test
    void getEmployeeInfo() {
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
        int employeeId = createNewEmployee(employeeParams);
        Employee employee = getEmployeeInfo(employeeId);

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(employeeParams, JsonObject.class);

        assertEquals(employee.getFirstName(), jsonObj.get("firstName").getAsString());
        assertTrue(employee.getActive(), jsonObj.get("isActive").getAsString());
        assertEquals(employee.getPhone(), jsonObj.get("phone").getAsString());
        assertEquals(employee.getBirthdate(), jsonObj.get("birthdate").getAsString());
        assertEquals(employee.getAvatar_url(), jsonObj.get("url").getAsString());
        assertEquals(employee.getEmail(), jsonObj.get("email").getAsString());
        assertEquals(employee.getLastName(), jsonObj.get("lastName").getAsString());
        assertEquals(employee.getMiddleName(), jsonObj.get("Olegovich").getAsString());
    }

    @Test
    public void patchEmployee() {
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
        int employeeId = createNewEmployee(employeeParams);
        String patchEmployeeParams = """
                {
                "lastName": "NotAndrew",
                  "email": "google@ya.ru",
                  "url": "Changed",
                  "phone": "99005553535",
                  "isActive": false
                }
                """;
        Employee employee = patchEmployeeInfo(employeeId, patchEmployeeParams);
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(patchEmployeeParams, JsonObject.class);
        assertEquals(employee.getLastName(), jsonObj.get("lastName").getAsString());
        assertEquals(employee.getEmail(), jsonObj.get("email").getAsString());
        assertEquals(employee.getUrl(), jsonObj.get("url").getAsString());
        assertEquals(employee.getPhone(), jsonObj.get("phone").getAsString());
        assertFalse(employee.getActive(), jsonObj.get("isActive").getAsString());
    }

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

    public Employee patchEmployeeInfo(int employeeId, String patchEmployeeParams) {
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

    public int createNewEmployee(String employeeParams) {
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
    public List<Map<String, Object>> getListOfEmployees() {
        String response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(employeeURL + "/" + companyId)
                .then()
                .assertThat().statusCode(200)
                .extract().asString();

        JsonPath jsonPath = new JsonPath(response);
        List<Map<String, Object>> employees = jsonPath.getList("$");

        return employees;
    }
    @Test
    void listEmployees() {
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
        String employeeParams2 = """
                {
                 "firstName": "asd",
                 "lastName": "fff",
                 "middleName": "ggg",
                 "companyId":""" + companyId + """
                 , "email": "zxa@mail.ru",
                 "url": "arrrrra",
                 "phone": "2132321312",
                 "birthdate": "2022-04-14",
                 "isActive": true
                 }
                """;
        createNewEmployee(employeeParams);
        createNewEmployee(employeeParams2);
        List<Map<String, Object>> x = getListOfEmployees();
        System.out.println(x);
    }

}


