package com.gruposonora.products.service;

import com.gruposonora.products.model.Produto;
import com.gruposonora.products.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;


@Service
public class ProdutoService {


    private ProdutoRepository produtoRepository;


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }


    public Produto buscarProdutoPorId(Long id) {
        try {
            Optional<Produto> produto = produtoRepository.findById(id);
            return produto.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade.", e);
        }
    }


    public void cadastrarProduto(Produto produto) {
        try {
            produtoRepository.save(produto);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade.", e);
        }
    }


    public List<Produto> listarProdutos() {
        try {
            List<Produto> produtos = produtoRepository.findAll();
            return produtos;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade.", e);
        }
    }


    public void excluirProdutoPorId(Long id) {
        try {
            produtoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade.", e);
        }
    }


    public Produto atualizarProduto(Produto produto) {
        try {
            Produto produtoAtualizado = produtoRepository.save(produto);
            return produtoAtualizado;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade.", e);
        }
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
