package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class Activity3 {

    RequestSpecification reqSpec;//request specification is and interface , and bo a class
    ResponseSpecification respSpec;

    int petId;

    @BeforeClass
    public void setUp() {
        reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .addHeader("Content-Type", "application/json")
                // .addHeader("Authorization","token sadhgyft7376823762")
                // .setAuth(RestAssured.oauth("gfddtr6587yugn"))
                .build();

        respSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                // Check response content type
                .expectContentType("application/json")
                // Check if response contains name property
                .expectBody("status", equalTo("alive"))
                // Build response specification
                .expectResponseTime(lessThan(3000L))
                .build();
    }



    @DataProvider
    public Object[][] petInfoProvider() {
        // Setting parameters to pass to test case
        Object[][] testData = new Object[][] {
                { 77777, "Riley", "alive" },
                { 77771, "Hansel", "alive" }
        };
        return testData;
    }


    @Test(priority=1,dataProvider = "petInfoProvider")
    public void postRequestTest(int id, String name, String status)
    {
        //request Body
        Map<String, Object> reqBody=new HashMap<>();
        reqBody.put("id", id);
        reqBody.put("name", name);
        reqBody.put("status", status);

        //Generate response
        Response response = given().log().all().spec(reqSpec).body(reqBody).when().post();

        //Extract petId
        petId = response.then().extract().path("id");

        //Assertions
// Assertions

        response.then().spec(respSpec);

    }

    @Test(priority=2,dataProvider = "petInfoProvider")
    public void getRequestTest(int id,String name, String status){
        //Generate response and assert
       Response getResponse= given().spec(reqSpec).pathParam("petId",id)
                .when().get("/{petId}");
        // Print response
        System.out.println(getResponse.asPrettyString());
        getResponse .then().spec(respSpec)
                .body("name", equalTo(name));;
    }

    @Test(dataProvider = "petInfoProvider", priority=3)
    public void DeleteRequestTest(int id, String name, String status){
        //Generate response and assert
        given().spec(reqSpec).pathParam("petId",id)
                .when().delete("/{petId}")
                .then().statusCode(200).time(lessThan(5000L));//or create another response spec
    }
}
