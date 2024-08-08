package com.gruposonora.products.controller;


import com.gruposonora.products.model.Cidade;
import com.gruposonora.products.service.CidadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cidade")
public class CidadeController {


    private CidadeService cidadeService;


    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }


    @PostMapping("/criar")
    public ResponseEntity<Cidade> cadastrarCidade(@RequestBody Cidade cidade) {

        try {
            cidadeService.cadastroCidade(cidade);
            return new ResponseEntity<Cidade>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Cidade>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/listar")
    public ResponseEntity<List<Cidade>> listarCidades() {
        List<Cidade> cidades = cidadeService.listarCidades();
        return new ResponseEntity<List<Cidade>>(cidades, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscarCidadePorId(@PathVariable Long id) {

        Cidade cidade = cidadeService.buscarCidadePorId(id);

        if(cidade != null) {
          return new ResponseEntity<>(cidade, HttpStatus.OK);
        } else {
            return new ResponseEntity<Cidade>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade> excluirCidadePorId(@PathVariable Long id) {

        Cidade cidade = cidadeService.buscarCidadePorId(id);

        if (cidade != null) {
            cidadeService.excluirCidadePorId(id);
            return new ResponseEntity<Cidade>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Cidade>(HttpStatus.NOT_FOUND);
        }
    }

}
