package com.example.ApiShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.ApiShop.model.FormaDePagamento;
import com.example.ApiShop.repositories.FormaPagamentoRepository;

public class FormaDePagamentoService {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional(readOnly = true)
    public FormaDePagamento findById(Long id) {
        Optional<FormaDePagamento> formaDePagamento = this.formaPagamentoRepository.findById(id);
        return formaDePagamento.orElseThrow(() -> new RuntimeException(
                "FormaDePagamento não encontrado! Id: " + id + ", Tipo: " + FormaDePagamento.class.getName()));
    }
    
    @Transactional(readOnly = true)
    public List<FormaDePagamento> findAll() {
        return formaPagamentoRepository.findAll();
    }


    public FormaDePagamento create(FormaDePagamento obj) {
        return this.formaPagamentoRepository.save(obj);
    }

    public FormaDePagamento update(Long id, FormaDePagamento updatedFormaDePagamento) {
        FormaDePagamento existingFormaDePagamento = findById(id);
        existingFormaDePagamento.setTipo_pagamento(updatedFormaDePagamento.getTipo_pagamento());
        existingFormaDePagamento.setParcela(updatedFormaDePagamento.getParcela());
        return formaPagamentoRepository.save(existingFormaDePagamento);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.formaPagamentoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
        }
    }
}
