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

    Employee employee = new Employee(true, "Alex", "Smirnof", "Egorovich",
            "9133546665", "ya@mail.ru", "1995-05-04", "pixels.com/123", companyId);


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
        int newEmployeeId = createNewEmployee(employee);
        assertNotNull(newEmployeeId);
        assertNotEquals(0, newEmployeeId);
    }

    @Test
    void getEmployeeInfo() {
        int employeeId = createNewEmployee(employee);
        Employee employeeInfo = getEmployeeInfo(employeeId);

        assertEquals(employee.getFirstName(), employeeInfo.getFirstName());
        assertEquals(employee.getActive(), employeeInfo.getActive());
        assertEquals(employee.getPhone(), employeeInfo.getPhone());
        assertEquals(employee.getBirthdate(), employeeInfo.getBirthdate());
        assertEquals(employee.getAvatar_url(), employeeInfo.getAvatar_url()); //тут баг
        assertEquals(employee.getEmail(), employeeInfo.getEmail()); // тут баг
        assertEquals(employee.getLastName(), employeeInfo.getLastName());
        assertEquals(employee.getMiddleName(), employeeInfo.getMiddleName());
        assertEquals(employee.getCompanyId(), employeeInfo.getCompanyId());
    }

    @Test
    public void patchEmployee() {
        int employeeId = createNewEmployee(employee);
        String patchEmployeeParams = """
                {
                "lastName": "changedLastName",
                  "email": "changedEmail@ya.ru",
                  "url": "Changed",
                  "phone": "66666666666",
                  "isActive": false
                }
                """;
        Employee employee1 = patchEmployeeInfo(employeeId, patchEmployeeParams);
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(patchEmployeeParams, JsonObject.class);
        assertEquals(jsonObj.get("lastName").getAsString(), employee1.getLastName() );
      //  assertEquals(employee.getLastName(), jsonObj.get("lastName").getAsString());
       // assertEquals(employee.getEmail(), jsonObj.get("email").getAsString());
       // assertEquals(employee.getPhone(), jsonObj.get("phone").getAsString());
        //assertFalse(employee.getActive(), jsonObj.get("isActive").getAsString());
    }
//    @Test
//    void listEmployees() {
//        Employee employee = new Employee(true, "Lef", "ddsada", "dsadas", "54554",
//                "wewew@mail.ru", "2002-04-15", "dsadas", companyId);
//        createNewEmployee(employee);
//        List<Employee> list = getListOfEmployees();
//        assertEquals(2, list.size());
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

    public int createNewEmployee(Employee employee) {
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

    public List<Employee> getListOfEmployees() {
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

}


