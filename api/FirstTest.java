package examples;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
public class FirstTest {

    //Base URI
    String baseURI="https://petstore.swagger.io/v2/pet";

    @Test
    public void getRequestTestwithQueryParam(){
        //generate response for get https://petstore.swagger.io/pet/findByStatus
        Response response=given().header("Content-Type","application/json")
                .queryParam("status","sold")
                .when().get(baseURI + "/findByStatus");


        //Get response body
        System.out.println(response.getBody().asString());
        System.out.println(response.getBody().asPrettyString());

        //Get response headers
        System.out.println(response.getHeaders().asList());
        System.out.println(response.getHeader("Content-Type"));

        //Get Individual properties
        //String petStatus = response.then().extract().jsonPath().using()
        String petStatus = response.then().extract().path("[0].status"); //if response is array then, first element's status property value

        System.out.println(petStatus);
        assertEquals(petStatus,"sold");

        /*to get size of hte response array
        response.then().body(Matchers.hasSize(3));*/

        //Assertions
        response.then().statusCode(200);
        response.then().time(lessThan(2000L));//hamcrest library , for LONG suffix L, for float --> f)
        response.then().body("[0].status",equalTo("sold"));

    }

    @Test
    public void getRequestWithPathParam(){
        //generate response for
        //https://petstore.swagger.io/v2/pet/{petId}
        given().header("Content-Type","application/json").pathParam("petId",19670278).
        when().get(baseURI + "/{petId}").
                then().statusCode(200).time(lessThan(2000L)).body("[0].status",equalTo("sold"));
    }

}
