package com.gruposonora.products.controller;

import com.gruposonora.products.model.Cidade;
import com.gruposonora.products.model.Produto;
import com.gruposonora.products.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProdutoControllerTeste {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProdutoService service;


    @Test
    @DisplayName("Deverá criar um produto sem erro")
    void criarProduto() throws Exception {

        //ARRANGE
        String json = """
                {
                    "nome": "Fone de ouvido",
                    "valor": "128",
                    "estoque": "7",
                    "cidade": {
                        "id": 1
                    }
                }""";

        //ACT
        var response = mvc.perform(
                post("/produto/criar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(201, response.getStatus());
    }


    @Test
    @DisplayName("Deverá devolver o erro 500 ao tentar criar um produto")
    void naoCriarProduto() throws Exception {

        //ARRANGE
        String json = "{}";

        Mockito.doThrow(new RuntimeException("Erro ao cadastrar produto"))
                .when(service).cadastrarProduto(Mockito.any(Produto.class));

        //ACT
        var response = mvc.perform(
                post("/produto/criar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(500, response.getStatus());
    }


    @Test
    @DisplayName("Deverá atualizar o produto sem erro")
    void atualizarProduto() throws Exception {

        // Produto atualizado com novos valores
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(3L);
        produtoAtualizado.setNome("Notebook Lenovo");
        produtoAtualizado.setValor(4250.0);
        produtoAtualizado.setEstoque(1.0);

        Cidade cidadeAtualizada = new Cidade();
        cidadeAtualizada.setId(2L);
        produtoAtualizado.setCidade(cidadeAtualizada);

        // Mockando o comportamento do serviço
        when(service.buscarProdutoPorId(3L)).thenReturn(produtoAtualizado);
        when(service.atualizarProduto(any(Produto.class))).thenReturn(produtoAtualizado);

        // JSON mockado que será enviado na requisição PUT
        String json = """
                {
                    "id": 3,
                    "nome": "Notebook Lenovo",
                    "valor": "4250.0",
                    "estoque": "1",
                    "cidade": {
                        "id": 2
                    }
                }""";

        //ACT
        var response = mvc.perform(
                put("/produto/atualizar")
                       .content(json)
                       .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }


    @Test
    @DisplayName("Não deverá atualizar o produto e retornar um erro")
    void naoAtualizarProduto() throws Exception {

        // Produto atualizado com novos valores
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(3L);
        produtoAtualizado.setNome("Notebook Lenovo");
        produtoAtualizado.setValor(4250.0);
        produtoAtualizado.setEstoque(1.0);

        Cidade cidadeAtualizada = new Cidade();
        cidadeAtualizada.setId(10L); // Cidade inexistente no banco de dados
        produtoAtualizado.setCidade(cidadeAtualizada);

        // Mockando o comportamento do serviço
        when(service.buscarProdutoPorId(3L)).thenReturn(produtoAtualizado);
        Mockito.doThrow(new RuntimeException("Erro ao atualizar produto"))
                .when(service).atualizarProduto(Mockito.any(Produto.class));

        // JSON mockado que será enviado na requisição PUT
        String json = """
                {
                    "id": 3,
                    "nome": "Notebook Lenovo",
                    "valor": 4250.0,
                    "estoque": 1,
                    "cidade": {
                        "id": 10
                    }
                }""";

        //ACT
        var response = mvc.perform(
                put("/produto/atualizar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(500, response.getStatus());
    }

}
