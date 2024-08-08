package com.gruposonora.products.repository;

import com.gruposonora.products.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
