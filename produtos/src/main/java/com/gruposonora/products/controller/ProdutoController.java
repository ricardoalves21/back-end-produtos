package com.gruposonora.products.controller;


import com.gruposonora.products.model.Produto;
import com.gruposonora.products.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200") // Quero permitir o CORS apenas para essa origem
@RequestMapping("/produto")
public class ProdutoController {

    private ProdutoService produtoService;


    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        try {
            Produto produto = produtoService.buscarProdutoPorId(id);

            if (produto != null) {
                return new ResponseEntity<Produto>(produto, HttpStatus.OK);
            } else {
                return new ResponseEntity<Produto>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar o produto: " + e.getMessage(), e);
        }
    }


    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listarProdutos() {
        try {
            List<Produto> produtos = produtoService.listarProdutos();
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade.", e);
        }
    }


    @PostMapping("/criar")
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        try {
            produtoService.cadastrarProduto(produto);
            return new ResponseEntity<Produto>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Produto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Produto> excluirProdutoPorId(@PathVariable Long id) {
        try {
            Produto produto = produtoService.buscarProdutoPorId(id);

            if (produto != null) {
                produtoService.excluirProdutoPorId(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar excluir o produto: " + e.getMessage(), e);
        }
    }


    @PutMapping("/atualizar")
    public ResponseEntity<Produto> atualizarProduto(@RequestBody Produto produto) {
        try {
            Produto produtoEncotrado = produtoService.buscarProdutoPorId(produto.getId());
            if (produtoEncotrado != null) {
                Produto produtoAtual = produtoService.atualizarProduto(produto);
                return new ResponseEntity<>(produtoAtual, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o produto: " + e.getMessage(), e);
        }
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
