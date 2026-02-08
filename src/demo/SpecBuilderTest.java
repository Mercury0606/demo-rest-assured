package demo;

import demo.pojo.AddPlace;
import demo.pojo.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
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


        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").
                addQueryParam("key", "qaclick123").
                setContentType(ContentType.JSON).
                build();

        ResponseSpecification resSpec = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).
                expectStatusCode(200).
                build();

        RequestSpecification res = given().spec(req)
                .body(requestBody);


        Response response = res.when().post("/maps/api/place/add/json")
                .then().spec(resSpec).extract().response();
        String resString = response.asString();
        System.out.println(resString);
    }
}
