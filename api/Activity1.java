package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity1 {
    static String baseURI ="https://petstore.swagger.io/v2/pet";

        // Create JSON request
        String reqBody = "{"
                + "\"id\": 717232,"
                + "\"name\": \"Riley\","
                + " \"status\": \"alive\""
                + "}";
        @Test(priority = 1)
        public void postRequest() {
            Response response = given().header("Content-Type", "application/json")
                    .body(reqBody)
                    .when().post(baseURI);

            // Assertion
            response.then().body("id", equalTo(717232));
            response.then().body("name", equalTo("Riley"));
            response.then().body("status", equalTo("alive"));
        }

        @Test(priority = 2)
        public void getRequest(){
           Response response= given().headers("Content-Type","application/json")
                   .when().pathParam("petID","717232")
                   .get(baseURI+"{petId");
           //Assertions
            response.then().body("id", equalTo(717232));
            response.then().body("name", equalTo("Riley"));
            response.then().body("status", equalTo("alive"));

        }

        @Test(priority = 3)
         public void delete(){
            Response response= given().contentType(ContentType.JSON)
                    .when().pathParam("petID","717232")
                    .delete(baseURI+"{petId}");
            //Assertions
            // Assertion
            response.then().body("code", equalTo(200));
            response.then().body("message", equalTo("717232"));
        }








    }
