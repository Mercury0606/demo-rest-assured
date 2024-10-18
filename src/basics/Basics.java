package basics;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Basics {
    public static void main(String[] args) {


        //given - all input details
        //when - Submit the API - resource
        //Then - validate the response

        RestAssured.baseURI="https://rahulshettyacademy.com";
        Payload payload = new Payload();
        String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(payload.addPlace()).
                when().post("maps/api/place/add/json").
                then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
        System.out.println(response);
        JsonPath jsonPath = new JsonPath(response);
        String placeId = jsonPath.get("place_id");
        System.out.println(placeId);

        //Update Place
        String newAddress = " Summer walk, Africa";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").
                body("{\r\n" +
                        "\"place_id\":\""+placeId+"\",\r\n" +
                        "\"address\":\""+newAddress+"\",\r\n" +
                        "\"key\":\"qaclick123\"\r\n" +
                        "}").
        when().put("maps/api/place/update/json").
                then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated")).extract().response().asString();


        //Get Palace
        String getPlaceResoponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId).
                when().get("maps/api/place/get/json").
                then().assertThat().log().all().statusCode(200).extract().response().asString();
        String acutalAddress = ReusableMethods.rawToJson(getPlaceResoponse).getString("address");

        Assert.assertEquals(newAddress,acutalAddress);
    }

}
