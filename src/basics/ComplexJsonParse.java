package basics;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath jsonPath = new JsonPath(Payload.coursePrice());
        int count = jsonPath.getInt("courses.size()");
//        Print no of courses returned by API
        System.out.println(count);
        int totalAmount = jsonPath.getInt("dashboard.purchaseAmount");
//        Print purchase amount
        System.out.println(totalAmount);
//        Print title of the first course
        String courseTitle = jsonPath.getString("courses[0].title");
        System.out.println(courseTitle);
//        Print title of the second course
        String courseTitle1 = jsonPath.getString("courses[1].title");
        System.out.println(courseTitle1);




//        Print all course titles and thier respective prices
        String Title;
        int Price;
        int Copies;
        int Sum;
        for (int i = 0; i < count; i++) {
            Title = jsonPath.getString("courses["+i+"].title");
            Price = jsonPath.getInt("courses["+i+"].price");
            System.out.println("The price of the course <" + Title + "> is: " + Price);
        }





        System.out.println(">>>>>Print no. of copies sold by RPA Course");
        for (int i = 0; i < count; i++) {
            Title = jsonPath.getString("courses["+i+"].title");
            Copies = jsonPath.getInt("courses["+i+"].copies");
            if (Title.equalsIgnoreCase("rpa")){
                System.out.println("The price of the course <" + Title + "> is: " + Copies);
                break;
            }

        }

        SumValidation sumValidation = new SumValidation();
        sumValidation.validate();
    }
}
