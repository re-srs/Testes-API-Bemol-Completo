# language: pt

@usuario
Funcionalidade: /usuarios

  @criarElistarUsuario
  Cenario: Criar usuario com sucesso
    Dado que preenchi o body
    Quando envio a solicitação para criar um usuario
    Então faço a validação da criação do usuario
    E faço a validação da consulta do usuario

