package com.gruposonora.products.model;

import jakarta.persistence.*;

import java.util.Set;


@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

//    @OneToMany(mappedBy = "cidade")
//    private Set<Produto> produtos;


    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

//    public Set<Produto> getProdutos() {
//        return produtos;
//    }
//
//    public void setProdutos(Set<Produto> produtos) {
//        this.produtos = produtos;
//    }
}
