package basics;

import  io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;
import static java.lang.Math.log;

public class BugTest {
    public static void main(String[] args) {
         RestAssured.baseURI = "https://mercuryzhong.atlassian.net";
        String creatIssueResponse = given().
                header("Content-Type", "application/json").
                header("Authorization", "Basic bWVyY3VyeXpob25nMDYwNkBnbWFpbC5jb206QVRBVFQzeEZmR0YwSk5WRHloSkdJdUFGbS1ueUo3ZFBUaHR1cE1iR1FHUVVIRTdHM09IQXFBaXk0YTQ3cVZNWmQtc2NaR0lSbFY4VFhSek1PYjRSUWNjMXI3OUl6X1Z4OW95TEhfN09fN19pQnRIcmlmVTBxZkVpUmNyQmZQdXl0NERBQ1NHaDQyelNoVmRyY19zX3RvcFExeC11ZTVDaDhXTFo3c1NqWWFsWGJIRjd3T2tZZ09jPThFQjQyNDFC").
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
                header("Authorization", "Basic bWVyY3VyeXpob25nMDYwNkBnbWFpbC5jb206QVRBVFQzeEZmR0YwSk5WRHloSkdJdUFGbS1ueUo3ZFBUaHR1cE1iR1FHUVVIRTdHM09IQXFBaXk0YTQ3cVZNWmQtc2NaR0lSbFY4VFhSek1PYjRSUWNjMXI3OUl6X1Z4OW95TEhfN09fN19pQnRIcmlmVTBxZkVpUmNyQmZQdXl0NERBQ1NHaDQyelNoVmRyY19zX3RvcFExeC11ZTVDaDhXTFo3c1NqWWFsWGJIRjd3T2tZZ09jPThFQjQyNDFC").
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
