package demo;

import demo.pojo.Api;
import demo.pojo.Courses;
import demo.pojo.GetCourse;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.RestAssured.*;

public class OAuthTest {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com/oauthapi/";
        //give - all input details
        //when - submit the API
        //Then - validate teh response
        String authRes = given().
                formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").
                formParam("grant_type", "client_credentials").
                formParam("scope", "trust").
                post("oauth2/resourceOwner/token").
                then().
                log().all().assertThat().statusCode(200).
                extract().response().asString();
        JsonPath jsonPath = new JsonPath(authRes);
        String access_token = jsonPath.getString("access_token");
        System.out.println(access_token);

        GetCourse getCourse = given().param("access_token", access_token).
                when().log().all().
                get("getCourseDetails").as((Type) GetCourse.class);
        System.out.println(">>>>>"+getCourse.getLinkedIn());
        System.out.println(">>>>>"+getCourse.getInstructor());
        System.out.println(">>>>>"+getCourse.getCourses());
        List<Api> apiCourses = getCourse.getCourses().getApi();
        for (Api api : apiCourses) {
            System.out.println(">>>>>>>>>>");
            System.out.println(">>>>>>>>>>");
            System.out.println("Course title:"+api.getCourseTitle());
//            System.out.println("Price:"+api.getPrice());
            System.out.println(">>>>>>>>>>");
            System.out.println(">>>>>>>>>>");
        }
    }
}
