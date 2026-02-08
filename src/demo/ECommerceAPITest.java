package demo;

import demo.pojo.LoginRequest;
import demo.pojo.LoginResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class ECommerceAPITest {
    public static void main(String[] args) {
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").
                setContentType(ContentType.JSON).
                build();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("405971848@qq.com");
        loginRequest.setUserPassword("Abcd1234.");

        RequestSpecification reqLogin = given().spec(req).body(loginRequest);
        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
        System.out.println(loginResponse.getToken());
        System.out.println(loginResponse.getUserId());

    }
}
