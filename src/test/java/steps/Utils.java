package steps;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class Utils {
    public static ValidatableResponse cadastro(String body, String url) {
        ValidatableResponse validatableResponse;
        validatableResponse =
                 given()
                .header("Authorization", getToken())
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .urlEncodingEnabled(false)
                .post(url)
                .then()
                .assertThat()
                .log().all();
        return validatableResponse;
}

    public static String getToken() {
        ValidatableResponse validatableResponse;
        String url = "https://serverest.dev/login";
        String token;
        validatableResponse =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                                "    \"email\": \"beltrano@qa.com.br\",\n" +
                                "    \"password\": \"teste\"\n" +
                                "}")
                        .when()
                        .post(url)
                        .then()
                        .assertThat()
                        .log().all();
        token = validatableResponse.extract().path("authorization");
        return token;
    }

    public static String nomeObjetoGerado(){
        Faker faker = new Faker(new Locale("pt-BR"));
        return faker.commerce().material();
    }
    public static String nomeGerado(){
        Faker faker = new Faker(new Locale("pt-BR"));
        return faker.name().fullName();
    }

    public static String emailGerado() {
        Faker faker = new Faker(new Locale("pt-BR"));
        String nomeCompleto = faker.name().fullName();
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("pt-BR"), new RandomService());
        String email = fakeValuesService.letterify(nomeCompleto).replace(".", "");
        email = StringUtils.deleteWhitespace(email + "@qa.com.br");
        return email;
    }

}
