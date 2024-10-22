package basics;

import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class SumValidation {

    JsonPath jsonPath = new JsonPath(Payload.coursePrice());
    int Count = jsonPath.getInt("courses.size()");
    String Title;
    int Price;
    int Copies;
    int AcutalSum = 0;
    int ExpectedSum = 0;

    public void SumofCourses() {
        for (int i = 0; i < Count; i++) {

            Title = jsonPath.getString("courses[" + i + "].title");
            Price = jsonPath.getInt("courses[" + i + "].price");
            Copies = jsonPath.getInt("courses[" + i + "].copies");
            ExpectedSum = Integer.valueOf(jsonPath.getString("dashboard.purchaseAmount"));
            AcutalSum += Price * Copies;
        }
        System.out.println("Actual sum is: " + AcutalSum);
        System.out.println("expected sum is: " + ExpectedSum);
    }

    public int getAcutalSum() {

        return AcutalSum;
    }

    public int getExpectedSum() {

        return ExpectedSum;
    }

    public void validate(){
        SumofCourses();
        int acutalSum = getAcutalSum();
        int expectedSum = getExpectedSum();
        Assert.assertEquals(acutalSum,expectedSum);
    }

}
