package basics;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider ="BooksData")
    public void addBook(String isbn,String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";
        String res = given().header("Content-Type", "application/json").
                body(Payload.addBookInfo(isbn, aisle)).
                when().post("/Library/Addbook.php").
                then().assertThat().statusCode(200).
                extract().response().asString();
        JsonPath jsonPath = ReusableMethods.rawToJson(res);
        String id = jsonPath.get("ID");
        System.out.println("id: " + id);
    }

    @DataProvider(name = "BooksData")
    public Object[][] getData() {
        //Array = collection of elements
        return new  Object[][]{
                {"asda", "9363"},
                {"sda","1423"},
                {"fasw","1524"}
        };
    }
}
