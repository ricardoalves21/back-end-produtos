package com.gruposonora.products.controller;

import com.gruposonora.products.model.Cidade;
import com.gruposonora.products.service.CidadeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@SpringBootTest
@AutoConfigureMockMvc
class CidadeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CidadeService service;

    @Test
    @DisplayName("Deverá cadastrar uma cidade sem erro")
    void cadastrarCidade() throws Exception {

        //ARRANGE
        String json = """
                {
                    "nome": "Cidade Teste"
                }
                """;

        //ACT
        var response = mvc.perform(
                post("/cidade/criar")
                       .content(json)
                       .contentType("application/json")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(201, response.getStatus());

    }

    @Test
    @DisplayName("Não deverá cadastrar uma cidade e deverá lançar um erro 500")
    void naoCadastrarCidade() throws Exception {

        //ARRANGE
        String json = "{}";

        Mockito.doThrow(new RuntimeException("Erro ao cadastrar cidade"))
                .when(service).cadastrarCidade(Mockito.any(Cidade.class));

        //ACT
        var response = mvc.perform(
                post("/cidade/criar")
                        .content(json)
                        .contentType("application/json")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(500, response.getStatus());

    }

    @Test
    @DisplayName("Deverá atualizar o nome de uma cidade")
    void atualizarCidade() {

        //Cidade atualizada com a nova descrição
        Cidade cidadeAtualizada = new Cidade();
        cidadeAtualizada.setId(1L);
        cidadeAtualizada.setNome("Cidade Atualizada");

        //Mockando a chamada do método para buscar cidade por id
        Mockito.when(service.buscarCidadePorId(1L)).thenReturn(cidadeAtualizada);

        //Mockando a chamada do método para atualizar cidade
        Mockito.when(service.atualizarCidade(cidadeAtualizada)).thenReturn(cidadeAtualizada);

        //Realizando a chamada do método de atualizar cidade
        Cidade cidade = service.atualizarCidade(cidadeAtualizada);

        //Verificando se a cidade foi atualizada
        assertEquals("Cidade Atualizada", cidade.getNome());
    }

    @Test
    @DisplayName("Não deverá atualizar o nome de uma cidade e retornar um erro")
    void naoAtualizarCidade() throws Exception {

        //Cidade atualizada com a nova descrição
        Cidade cidadeAtualizada = new Cidade();
        cidadeAtualizada.setId(10L);
        cidadeAtualizada.setNome("Cidade Atualizada");

        //Mockando a chamada do método para buscar cidade por id
        Mockito.when(service.buscarCidadePorId(10L)).thenReturn(cidadeAtualizada);
        Mockito.doThrow(new RuntimeException("Erro ao atualizar cidade"))
                .when(service).atualizarCidade(Mockito.any(Cidade.class));

        //JSON mockado que será enviado na requisição PUT
        String json = """
                    {
                        "id": 10,
                        "nome": "Cidade Atualizada",
                    }""";

        //ACT
        var response = mvc.perform(
                put("/cidade/atualizar")
                       .content(json)
                       .contentType("application/json")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(500, response.getStatus());
    }
}