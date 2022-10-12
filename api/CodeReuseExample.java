package examples;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class CodeReuseExample {

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
                .expectResponseTime(lessThan(3000L))
                .expectBody("status", equalTo("alive"))
                .build();
    }


    @Test(priority=1)
    public void postRequestTest()
    {
        //request Body
        Map<String, Object> reqBody=new HashMap<>();
        reqBody.put("id", 9877);
        reqBody.put("name", "Maggie");
        reqBody.put("status", "alive");

        //Generate response
        Response response = given().log().all().spec(reqSpec).body(reqBody).when().post();

        //Extract petId
        petId = response.then().extract().path("id");

        //Assertions
        response.then().spec(respSpec);//all the assertions are in respSpec
        response.then().spec(respSpec).body("name", equalTo("Maggie"));//when additional assertions need to be done

    }

    @Test(priority=2)
    public void getRequestTest(){
        //Generate response and assert
        given().spec(reqSpec).pathParam("petId",petId)
                .when().get("/{petId}")
                .then().spec(respSpec);
    }

    @Test(priority=3)
    public void DeleteRequestTest(){
        //Generate response and assert
        given().spec(reqSpec).pathParam("petId",petId)
                .when().delete("/{petId}")
                .then().statusCode(200).time(lessThan(5000L));//or create another response spec
    }

}
