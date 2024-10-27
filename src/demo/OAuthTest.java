package demo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class OAuthTest {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token";
        String authRes = given().
                formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").
                formParam("grant_type", "client_credentials").
                formParam("scope", "trust").
                post().
                then().
                log().all().assertThat().statusCode(200).
                extract().response().asString();
        JsonPath jsonPath = new JsonPath(authRes);
        String access_token = jsonPath.getString("access_token");
        System.out.println(access_token);


    }
}
