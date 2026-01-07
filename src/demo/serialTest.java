package demo;

import demo.pojo.AddPlace;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class serialTest {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com/oauthapi/";
        AddPlace requestBody = new AddPlace();
        Response res = given().queryParam("key","qa123")
                .body(requestBody.getLocation())
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();
        String resString = res.asString();
        System.out.println(resString);
    }
}
