package basics;

import  io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;
import static java.lang.Math.log;

public class BugTest {
    public static void main(String[] args) {
         RestAssured.baseURI = "https://mercuryzhong0606.atlassian.net";
        String creatIssueResponse = given().
                header("Content-Type", "application/json").
                header("Authorization", "Basic TWVyY3VyeXpob25nMDYwNkBnbWFpbC5jb206QVRBVFQzeEZmR0YwUGtqUVl0ME50RnRha1Q4b2I2bHNJYjFBOTFEalNrazF4cHhTQjMtcHBMZno1czhNRG4wZHRwT3dGUjF2ZERGZGlFTWptemtCRlFncW9QODNGLW9HQzE4cnRtZUZfd3picjJnUFJIUlQxZWxYQm9sd2dBR2lPTmtBd0tJUHJFVjdlczhEaXduRURpbkZmdUg2eDhhQnpKbUZLbkZhNUlmNHlGOTRJVXlNMVc0PUUyRkY2RDNG").
                body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":{\n" +
                        "          \"key\": \"SCRUM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"links are not working\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}").
                post("/rest/api/3/issue").
                then()
                .log().all().assertThat().statusCode(201).
                extract().response().asString();

        JsonPath jsonPath = new JsonPath(creatIssueResponse);
        String issueId = jsonPath.getString("id");
        System.out.println(issueId);


//        Add attachment
        File file = new File("src/test/resources/beijing.html");
        String creatIssueResponse01 = given().
                pathParams("key",issueId).
                header("Authorization", "Basic TWVyY3VyeXpob25nMDYwNkBnbWFpbC5jb206QVRBVFQzeEZmR0YwUGtqUVl0ME50RnRha1Q4b2I2bHNJYjFBOTFEalNrazF4cHhTQjMtcHBMZno1czhNRG4wZHRwT3dGUjF2ZERGZGlFTWptemtCRlFncW9QODNGLW9HQzE4cnRtZUZfd3picjJnUFJIUlQxZWxYQm9sd2dBR2lPTmtBd0tJUHJFVjdlczhEaXduRURpbkZmdUg2eDhhQnpKbUZLbkZhNUlmNHlGOTRJVXlNMVc0PUUyRkY2RDNG").
                header("X-Atlassian-Token", "no-check").
                multiPart("file", file).
                post("/rest/api/2/issue/{key}/attachments").
                then().
                log().all().assertThat().statusCode(200).
                extract().response().asString();
        jsonPath = new JsonPath(creatIssueResponse01);
        String issueId01 = jsonPath.getString("id");
        System.out.println(issueId01);



    }
}
