package com.example.ApiShop.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ApiShop.model.FormaDePagamento;
import com.example.ApiShop.service.FormaDePagamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/formaPagamento")
@Validated
public class FormaDePagamentoController {
    
    @Autowired
    private FormaDePagamentoService formaDePagamentoService;

    @GetMapping("/{id}")
    public ResponseEntity<FormaDePagamento> findById(@PathVariable Long id) {
        FormaDePagamento obj = formaDePagamentoService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping
    public ResponseEntity<List<FormaDePagamento>> findAll() {
        List<FormaDePagamento> FormaDePagamentos = formaDePagamentoService.findAll();
        return ResponseEntity.ok(FormaDePagamentos);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody FormaDePagamento obj) {
        FormaDePagamento savedFormaDePagamento = formaDePagamentoService.create(obj);
        URI uri = URI.create("/formaDePagamento/" + savedFormaDePagamento.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody FormaDePagamento obj, @PathVariable Long id) {
        obj.setId(id);
        obj = this.formaDePagamentoService.update(id, obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        formaDePagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
