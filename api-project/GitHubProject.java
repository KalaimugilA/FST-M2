package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHubProject {

    RequestSpecification requestSpec;
    int id=0;

    @BeforeClass
    public void setUp() {

        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","token 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")
                .build();

    }

    @Test(priority = 1)
    public void postRequestTest()
    {   String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCnydgurQu3HlplEVs13l2Ldhh5lA6g+YwqgTSTEQoliTHG+xz52yJwMEbNmsWjBjW7/M4cmfv1Pp/JGY0GJqFVqx1yLAKbbLRqrZIm3m6qPJSfgNMP8ceGDIBxm3RY4E2rLK2NWvkrjOzoBtKZRoCOih1VLjOF++W12KVEf89pGtfpQYsrllcTnCYmxCUY1bxmXUPATn2QeMz03zJk+raoa4+dbrzY204yHnsrBROZNxIK942wnu5IrfzEhiecpqw8kkyNWnbe+tMleJrIiXnRvbKn2G8HeKJVDxO8dB3b3yOTf+PjH4Vp3bjUVuVcXrisWD+wcPIVYNIkJLBjrexF \"}";
        Response response =given().spec(requestSpec)
                .log().all()
                .basePath("/user/keys")
                .body(reqBody)
                .when().post();
        response.then().statusCode(201);
        id= response.then().extract().body().path("id");

    }
    @Test(priority = 2)
    public void getRequestTest()
    {  //Generate response
        Response response =given().spec(requestSpec)
                .log().all()
                .when().get("/user/keys/"+id);

        //Assertions
        response.then().statusCode(200);;
    }

    @Test(priority = 3)
    public void deleteRequestTest()
    {  //Generate response
        Response response =given().spec(requestSpec)
                .log().all()
                .when().delete("/user/keys/"+id);

        //Assertions
        response.then().statusCode(204);
        }


}
