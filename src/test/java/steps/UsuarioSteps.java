package steps;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static steps.Utils.*;

public class UsuarioSteps {


    String mensagem = "";
    String id;
    int statusCode;
    String url = "https://serverest.dev/usuarios";
    String url_lista_usuario = "https://serverest.dev/usuarios?_id=";

    String body;

    @Dado("que preenchi o body")
    public void que_preenchi_o_body() {
        body = "{\n" +
                "  \"nome\": \""+nomeGerado()+"\",\n" +
                "  \"email\": \""+emailGerado()+"\",\n" +
                "  \"password\": \"teste\",\n" +
                "  \"administrador\": \"true\"\n" +
                "}\n";
    }

    @Quando("envio a solicitação para criar um usuario")
    public void envio_a_solicitação_para_criar_um_usuario() {
        ValidatableResponse validatableResponse = cadastro(body, url);
        mensagem = validatableResponse.extract().path("message");
        statusCode = validatableResponse.extract().statusCode();
        id = validatableResponse.extract().path("_id");
    }

    @Então("faço a validação da criação do usuario")
    public void faço_a_validação_da_criação_do_usuarioo() {
        assertEquals("Cadastro realizado com sucesso", mensagem);
        assertEquals(201, statusCode);
    }
    @Então("faço a validação da consulta do usuario")
    public void faço_a_validação_da_mensagem_de_retorno_da_consulta() {
        url_lista_usuario = "https://serverest.dev/usuarios?_id=";
        ValidatableResponse validatableResponse =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(url_lista_usuario+id)
                        .then()
                        .assertThat()
                        .log().all();
        mensagem = validatableResponse.extract().path("message");
        statusCode = validatableResponse.extract().statusCode();
        assertEquals(200, statusCode);
    }
}
