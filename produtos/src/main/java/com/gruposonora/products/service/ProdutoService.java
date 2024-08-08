package com.gruposonora.products.service;

import com.gruposonora.products.model.Produto;
import com.gruposonora.products.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProdutoService {


    private ProdutoRepository produtoRepository;


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }


    public Produto buscarProdutoPorId(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElse(null);
    }


    public void cadastrarProduto(Produto produto) {
        produtoRepository.save(produto);
    }


    public List<Produto> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos;
    }

    public void excluirProdutoPorId(Long id) {
        produtoRepository.deleteById(id);
    }

    public Produto atualizaProduto(Produto produto) {
        Produto produtoAtualizado = produtoRepository.save(produto);
        return produtoAtualizado;
    }
}
