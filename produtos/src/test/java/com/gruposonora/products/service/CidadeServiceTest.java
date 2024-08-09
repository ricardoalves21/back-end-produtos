package com.gruposonora.products.service;

import com.gruposonora.products.model.Cidade;
import com.gruposonora.products.repository.CidadeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CidadeServiceTest {

    @InjectMocks
    private CidadeService service;

    @Mock
    private CidadeRepository cidadeRepository;



    @Test
    @DisplayName("Cadastrar uma cidade sem retornar nenhum erro")
    void cadastrarCidade() {

        //Arrange
        Cidade cidadeCadastrada = new Cidade();
        cidadeCadastrada.setNome("Araçatuba");

        //Act
        service.cadastrarCidade(cidadeCadastrada);

        //Assert
        verify(cidadeRepository).save(cidadeCadastrada);
    }


    @Test
    @DisplayName("Não poderá cadastrar uma cidade, deverá retornar erro")
    void naoCadastrarCidade() {

        // criar uma exceção para salvar um objeto nulo
        doThrow(new DataIntegrityViolationException("Objeto nulo")).when(cidadeRepository).save(null);

        // Verificar se a classe RuntimeException é lançada quando o parâmetro é nulo
        assertThrows(RuntimeException.class, () -> service.cadastrarCidade(null));

        // Verificar se o método save() foi chamado com o parâmetro nulo
        verify(cidadeRepository).save(null);
    }


    @Test
    @DisplayName("Atualizar os dados de uma cidade")
    void atualizarCidade() {

        //Arrange
        Cidade cidadeAtualizada = new Cidade();
        cidadeAtualizada.setNome("Ituitaba");

        //Act
        service.atualizarCidade(cidadeAtualizada);

        //Assert
        verify(cidadeRepository).save(cidadeAtualizada);
    }


    @Test
    @DisplayName("Não atualizar os dados de uma cidade e retornar erro")
    void naoAtualizarCidade() {

        // Criei uma instância de Cidade com ID nulo
        Cidade cidadeAtualizada = new Cidade();
        cidadeAtualizada.setNome("Ituitaba");

        // Simulei a exceção que será lançada ao tentar salvar uma cidade sem ID
        when(cidadeRepository.save(cidadeAtualizada))
        .thenThrow(new InvalidDataAccessApiUsageException("ID não informado"));

        // Verificar se a RuntimeException é lançada quando a cidade sem ID é passada
        assertThrows(RuntimeException.class, () -> service.atualizarCidade(cidadeAtualizada));

        // Verificar se o método save() foi chamado com a cidade sem ID
        verify(cidadeRepository).save(cidadeAtualizada);
    }


}