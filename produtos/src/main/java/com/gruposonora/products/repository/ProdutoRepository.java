package com.gruposonora.products.repository;

import com.gruposonora.products.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
