package com.gruposonora.products.service;


import com.gruposonora.products.model.Cidade;
import com.gruposonora.products.repository.CidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CidadeService {

    private CidadeRepository cidadeRepository;


    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }


    public void cadastroCidade(Cidade cidade) {
        cidadeRepository.save(cidade);
    }


    public List<Cidade> listarCidades() {
        List<Cidade> cidades = cidadeRepository.findAll();
        return cidades;
    }


    public Cidade buscarCidadePorId(Long id) {
        Optional<Cidade> cidade = cidadeRepository.findById(id);
            return cidade.orElse(null);
    }


    public void excluirCidadePorId(Long id) {
        cidadeRepository.deleteById(id);
    }
}
