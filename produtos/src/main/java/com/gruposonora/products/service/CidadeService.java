package com.gruposonora.products.service;


import com.gruposonora.products.model.Cidade;
import com.gruposonora.products.repository.CidadeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;


@Service
public class CidadeService {

    private CidadeRepository cidadeRepository;


    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }


    public void cadastrarCidade(Cidade cidade) {
        try {
            cidadeRepository.save(cidade);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar cidade.", e);
        }
    }


    public List<Cidade> listarCidades() {
        try {
            List<Cidade> cidades = cidadeRepository.findAll();
            return cidades;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade.", e);
        }
    }


    public Cidade buscarCidadePorId(Long id) {
        try {
            Optional<Cidade> cidade = cidadeRepository.findById(id);
            return cidade.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade.", e);
        }
    }


    public void excluirCidadePorId(Long id) {
        try {
            cidadeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade.", e);
        }
    }


    public Cidade atualizarCidade(Cidade cidade) {
        try {
            Cidade cidadeAtualizada = cidadeRepository.save(cidade);
            return cidadeAtualizada;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade.", e);
        }
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
