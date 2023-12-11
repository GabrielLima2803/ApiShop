package com.example.ApiShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ApiShop.model.FormaDePagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaDePagamento, Long> {
}
