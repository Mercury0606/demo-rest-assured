package demo;

import demo.pojo.AddPlace;
import demo.pojo.Location;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;

public class serialTest {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com/";
        AddPlace requestBody = new AddPlace();
        requestBody.setAccuracy(50);
        requestBody.setAddress("29, in N out, LA, CA");
        requestBody.setPhoneNo("12345678");
        requestBody.setName("Merc");
        ArrayList<String> myList = new ArrayList<>();
        myList.add("Nike");
        myList.add("Adidas");
        requestBody.setTypes(myList);

        Location location = new Location();
        location.setLat(38.14141);
        location.setLng(38.14141);
        requestBody.setLocation(location);

        Response res = given().queryParam("key","qa123")
                .body(requestBody)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();
        String resString = res.asString();
        System.out.println(resString);
    }
}
