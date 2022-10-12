package Project;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class RestAssuredProjectTest {
    String sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCRvXRFSJ/Td6S05pjVic0HET2G8I+bXhTcLAOu8romdik88sDYK5maHbwDJjSmvTkv09hcsR5WQli/GDnWZ9Cm4IAMUJDs1hqblC6NZd+YxjFqKlo6QkKMB4sNtPEf9EBLv3HYqOy+VjxJGLPdf0sp9HWbmZhrA0bTNA4z/jxrqeP8X06o8pspQnjHH86dPRV5Oew1sLin73Ap6yBQaajCUetC84VKYAxHKEhZS7iwQqa9chjgd5WGLdI4/u5FS4guX9L/DZhBg1+i6IYFSNzBW29y5qBOOtXbUqEoLFD01HpvKJ0ohs1NOimzo/XkPa+c51irNOrlH5Cmcw2r1MXj";
    int sshId;
    RequestSpecification reqSpec;


    @BeforeClass
    public void setUp()
    {
        reqSpec=new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization","token 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")
                .build();

    }

    @Test(priority = 1)
    public void postRequest() {
        Map <String,String> postBody = new HashMap<>();
        postBody.put("title", "TestAPIKey");
        postBody.put("key", sshKey);

        //Generate response
        Response response = given().spec(reqSpec).body(postBody)
                .when().post("/user/keys");

        System.out.println(response.getBody().asPrettyString());
        //Extract key
        sshId = response.then().extract().path("id");

        //Assertions
        response.then().statusCode(201);
    }

    @Test (priority = 2)
    public void getRequest() {
        Response response = given().spec(reqSpec)
                .pathParam("keyId", sshId)
                .when()
                .get("/user/keys/{keyId}");

        System.out.println(response.getBody().asPrettyString());
        response.then().statusCode(200).body("key", equalTo(sshKey));
    }

    @Test (priority = 3)
    public void deleteRequest(){
        Response response = given().spec(reqSpec)
                .pathParam("keyId",sshId)
                .when().delete("/user/keys/{keyId}");
        response.then().statusCode(204);

    }

    }


