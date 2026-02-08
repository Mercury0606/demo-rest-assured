package demo;

import demo.pojo.LoginRequest;
import demo.pojo.LoginResponse;
import demo.pojo.OrderDetails;
import demo.pojo.Orders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ECommerceAPITest {
    public static void main(String[] args) {
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").
                setContentType(ContentType.JSON).
                build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("405971848@qq.com");
        loginRequest.setUserPassword("Abcd1234.");

        RequestSpecification reqLogin = given().spec(req).body(loginRequest);
        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
        System.out.println(loginResponse.getToken());
        String token = loginResponse.getToken();
        System.out.println(loginResponse.getUserId());
        String userId = loginResponse.getUserId();


        //add product
//

        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").
                addHeader("authorization",token).
                build();
        RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq).
                param("productName", "Laptop").
                param("productAddedBy", userId).
                param("productCategory", "fashion").
                param("productSubCategory", "shirts").
                param("productPrice", "11500").
                param("productDescription", "Lenova").
                param("productFor", "men").
                multiPart("productImage", new File("src/test/resources/productImage.jpg"));

        String addProductResponse = reqAddProduct.when().post("api/ecom/product/add-product").then().log().all().extract().response().asString();
        JsonPath jsonPath = new JsonPath(addProductResponse);
        String  productId = jsonPath.get("productId");

        //Create Order
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").
                addHeader("authorization",token).setContentType(ContentType.JSON).
                build();
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCountry("India");
        orderDetails.setProductOrderedId(productId);

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetails);


        Orders orders = new Orders();
        orders.setOrders(orderDetailsList);


        RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);
        String string = createOrderReq.when().post("/api/ecom/order/create-order").
                then().
                log().all().
                extract().response().asString();
        JsonPath jsonPath1 = new JsonPath(string);
        System.out.println(jsonPath1);
    }
}
