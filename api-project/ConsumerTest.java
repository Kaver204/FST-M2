package liveProject;


import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //Headers Object
    Map<String,String> headers = new HashMap<>();

    //Resource Path
    String resourcePath = "/api/users";

    //Generate a contract
    @Pact(consumer ="UserConsumer", provider ="UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){
       //Add the headers
       headers.put("Content-Type","application/json");

       //create json body for request and response
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id",777)
                .stringType("firstName","Joe")
                .stringType("lastName","March")
                .stringType("email", "joe@bookie.com");

        //write the fragment to pact
        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                .method("POST")
                .headers(headers)
                .path(resourcePath)
                .body(requestResponseBody)
                .willRespondWith()
                .status(201)
                .body(requestResponseBody)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider", port ="8282")
    public void consumerTest(){
        //BaseURI
        String requestURI ="http://localhost:8282"+resourcePath;

        //Request body
        Map<String,Object> reqBody = new HashMap<>();
        reqBody.put("id",777);
        reqBody.put("firstName","Joe");
        reqBody.put("lastName","March");
        reqBody.put("email", "joe@bookie.com");

        //Generate response
        given().headers(headers).body(reqBody)
                .when().post(requestURI)
                .then().statusCode(201).log().all();

    }




}
