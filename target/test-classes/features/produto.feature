# language: pt

@produto
Funcionalidade: /produtos

  @criarElistarProduto
  Cenario: Criar produto com sucesso
    Dado que preenchi o body com sucesso
    Quando envio a solicitação para criar um produto
    Então faço a validação da criação do produto
    E faço a validação da consulta do produto




