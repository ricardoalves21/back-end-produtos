package com.gruposonora.products.controller;


import com.gruposonora.products.model.Cidade;
import com.gruposonora.products.service.CidadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200") // Quero permitir o CORS apenas para essa origem
@RequestMapping("/cidade")
public class CidadeController {


    private CidadeService cidadeService;


    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }


    @PostMapping("/criar")
    public ResponseEntity<Cidade> cadastrarCidade(@RequestBody Cidade cidade) {
        try {
            cidadeService.cadastrarCidade(cidade);
            return new ResponseEntity<Cidade>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Cidade>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/listar")
    public ResponseEntity<List<Cidade>> listarCidades() {
        try {
            List<Cidade> cidades = cidadeService.listarCidades();
            return new ResponseEntity<List<Cidade>>(cidades, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade.", e);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscarCidadePorId(@PathVariable Long id) {
        try {
            Cidade cidade = cidadeService.buscarCidadePorId(id);

            if(cidade != null) {
                return new ResponseEntity<>(cidade, HttpStatus.OK);
            } else {
                return new ResponseEntity<Cidade>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o produto: " + e.getMessage(), e);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade> excluirCidadePorId(@PathVariable Long id) {
        try {
            Cidade cidade = cidadeService.buscarCidadePorId(id);

            if (cidade != null) {
                cidadeService.excluirCidadePorId(id);
                return new ResponseEntity<Cidade>(HttpStatus.OK);
            } else {
                return new ResponseEntity<Cidade>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o produto: " + e.getMessage(), e);
        }
    }


    @PutMapping("/atualizar")
    public ResponseEntity<Cidade> atualizarCidade(@RequestBody Cidade cidade) {
        try {
            Cidade encontrarCidade = cidadeService.buscarCidadePorId(cidade.getId());

            if(encontrarCidade != null) {
                Cidade cidadeAtualizada = cidadeService.atualizarCidade(cidade);
                return new ResponseEntity<>(cidadeAtualizada, HttpStatus.OK);
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
