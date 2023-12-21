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

public class ProdutoSteps {

    String mensagem = "";
    String id;
    int statusCode;
    String url = "https://serverest.dev/produtos";

    String url_lista_produto = "https://serverest.dev/produtos?_id=";
    //Support
    String body;

  @Dado("que preenchi o body com sucesso")
    public void que_preenchi_o_body_com_sucesso() {
        body = "{\n" +
                "  \"nome\": \""+nomeObjetoGerado()+"\",\n" +
                "  \"preco\": 900,\n" +
                "  \"descricao\": \""+nomeObjetoGerado()+"\",\n" +
                "  \"quantidade\":  5\n" +
                "}\n";
    }

    @Quando("envio a solicitação para criar um produto")
    public void envio_a_solicitação_para_criar_um_produto() {
    ValidatableResponse validatableResponse = cadastro(body, url);
        mensagem = validatableResponse.extract().path("message");
        statusCode = validatableResponse.extract().statusCode();
        id = validatableResponse.extract().path("_id");
    }

    @Então("faço a validação da criação do produto")
    public void faço_a_validação_da_criação_do_produto() {
        assertEquals("Cadastro realizado com sucesso", mensagem);
        assertEquals(201, statusCode);
    }

    @Dado("que desejo consultar um produto com sucesso passando ID")
    public void que_desejo_consultar_um_produto_com_sucesso_passando_id() {
           get(url_lista_produto+id);
    }
    @Então("faço a validação da consulta do produto")
    public void faço_a_validação_da_consulta_do_produto() {
        url_lista_produto = "https://serverest.dev/produtos?_id="+id;
        ValidatableResponse validatableResponse =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(url_lista_produto)
                        .then()
                        .assertThat()
                        .log().all();
        mensagem = validatableResponse.extract().path("message");
        statusCode = validatableResponse.extract().statusCode();
        assertEquals(200, statusCode);
    }
}
