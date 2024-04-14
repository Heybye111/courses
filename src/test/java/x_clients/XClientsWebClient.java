package x_clients;

import io.restassured.http.ContentType;
import x_clients.model.Company;
import x_clients.model.CreateCompany;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import static io.restassured.RestAssured.given;

public class XClientsWebClient {
    public static final String URL = "https://x-clients-be.onrender.com/company";
    public static final String URL_ID = "https://x-clients-be.onrender.com/company/{id}";
    public static final String URL_AUTH = "https://x-clients-be.onrender.com/auth/login";

    public String getToken(String login, String pass) {
        String creds = "{\"username\": \"" + login + "\",\"password\": \"" + pass + "\"}";

        // авторизоваться
        return given().log().all()
                .body(creds)
                .contentType(ContentType.JSON)
                .when().post(URL_AUTH)
                .then().log().all()
                .extract().path("userToken");
    }

    public int createCompany(String name, String desc, String token) {
        CreateCompany createCompany = new CreateCompany(name, desc);

        // создать компанию
        return given()
                .log().all()
                .body(createCompany)
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .when()
                .post(URL)
                .then()
                .log().all()
                .extract().path("id");
    }

    public Company getCompanyInfo(int compId) {
        return given()
                .log().all()
                .pathParams("id", compId)
                .get(URL_ID)
                .then()
                .log().all()
                .extract().as(Company.class);
    }


    public static void main(String[] args) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd MMM yyyy")
                .toFormatter(new Locale("ru","RU"));
        System.out.println(LocalDate.parse("13 апр 2024", formatter));
    }
}