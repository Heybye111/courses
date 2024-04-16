package x_clients;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import ru.inno.courses.certification.XClients;
import ru.inno.courses.certification.model.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;


import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class XClientsContractTests {

    int companyId;

    Employee employee = new Employee(true, "Alex", "Smirnof", "Egorovich",
            "9133546665", "ya@mail.ru", "1995-05-04", "pixels.com/123", companyId);

    @BeforeAll
    public static void setup() throws IOException {
        XClients.getToken("bloom", "fire-fairy");
    }

    @BeforeEach
    public void setUp() {
        companyId = XClients.createNewCompany("Beta bank");
    }

    @Test
    void createNewEmployee() {
        int newEmployeeId = XClients.createNewEmployee(employee);
        assertNotNull(newEmployeeId);
        assertNotEquals(0, newEmployeeId);
    }

    @Test
    void getEmployeeInfo() {
        int employeeId = XClients.createNewEmployee(employee);
        Employee employeeInfo = XClients.getEmployeeInfo(employeeId);

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
    void listEmployees() {
        XClients.createNewEmployee(employee);
        List<Employee> list = XClients.getListOfEmployees(companyId);
        assertEquals(2, list.size());
    }

    @Test
    public void patchEmployee() {
        int employeeId = XClients.createNewEmployee(employee);
        String patchEmployeeParams = """
                {
                "lastName": "changedLastName",
                  "email": "changedEmail@ya.ru",
                  "url": "Changed",
                  "phone": "66666666666",
                  "isActive": false
                }
                """;
        Employee employee1 = XClients.patchEmployeeInfo(employeeId, patchEmployeeParams);
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(patchEmployeeParams, JsonObject.class);
        assertEquals(jsonObj.get("lastName").getAsString(), employee1.getLastName());

    }


}


