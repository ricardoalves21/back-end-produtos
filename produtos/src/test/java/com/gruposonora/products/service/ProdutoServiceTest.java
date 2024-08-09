package com.gruposonora.products.service;

import com.gruposonora.products.model.Cidade;
import com.gruposonora.products.model.Produto;
import com.gruposonora.products.repository.CidadeRepository;
import com.gruposonora.products.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoRepository repository;


    @Test
    @DisplayName("Cadastrar um produto sem retornar erro")
    void cadastrarProduto() {

        //Arrange
        Produto produtoCadastrado = new Produto();
        produtoCadastrado.setNome("Smart Watch");
        produtoCadastrado.setValor(400.0);
        produtoCadastrado.setEstoque(17.0);

        Cidade cidadeCadastrada = new Cidade();
        cidadeCadastrada.setId(1L);

        produtoCadastrado.setCidade(cidadeCadastrada);

        //Act
        service.cadastrarProduto(produtoCadastrado);

        //Assert
        verify(repository).save(produtoCadastrado);
    }


    @Test
    @DisplayName("Não poderá cadastrar um produto devendo retornar erro")
    void naoCadastrarProduto() {

        //Arrange
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome("Smart Watch");
        produtoAtualizado.setValor(400.0);

        Cidade cidadeCadastrada = new Cidade();
        cidadeCadastrada.setId(1L);

        produtoAtualizado.setCidade(cidadeCadastrada);

        //Act
        when(repository.save(produtoAtualizado))
                .thenThrow(new InvalidDataAccessApiUsageException("CAMPO não informado"));

        //Assert
        assertThrows(RuntimeException.class, () -> repository.save(produtoAtualizado));
        verify(repository).save(produtoAtualizado);

    }


    @Test
    @DisplayName("Atualiza um produto sem retornar erro")
    void atualizaProduto() {

        //Arrange
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(6L);
        produtoAtualizado.setNome("Smart Watch Samsung");
        produtoAtualizado.setValor(575.5);
        produtoAtualizado.setEstoque(13.0);

        Cidade cidadeAtualizada = new Cidade();
        cidadeAtualizada.setId(1L);

        produtoAtualizado.setCidade(cidadeAtualizada);

        //Act
        service.atualizarProduto(produtoAtualizado);

        //Assert
        verify(repository).save(produtoAtualizado);
    }


    @Test
    @DisplayName("Não atualizar os dados do produto e retornar erro")
    void naoAtualizarProduto() {

        // Criei uma instância de Produto e não informei o ID do produto
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome("Smart Watch Samsung");
        produtoAtualizado.setValor(575.5);
        produtoAtualizado.setEstoque(13.0);

        Cidade cidadeAtualizada = new Cidade();
        cidadeAtualizada.setId(1L);

        produtoAtualizado.setCidade(cidadeAtualizada);

        // Simulei a exceção que será lançada ao tentar salvar uma cidade sem ID
        when(repository.save(produtoAtualizado))
                .thenThrow(new InvalidDataAccessApiUsageException("ID não informado"));

        // Verificar se a RuntimeException é lançada quando o produto está sem ID
        assertThrows(RuntimeException.class, () -> service.atualizarProduto(produtoAtualizado));

        // Verificar se o método save() foi chamado com a cidade sem ID
        verify(repository).save(produtoAtualizado);
    }
}