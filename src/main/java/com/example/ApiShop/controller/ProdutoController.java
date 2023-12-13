package com.example.ApiShop.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.ApiShop.model.Produto;
import com.example.ApiShop.service.ProdutoService;

import jakarta.validation.Valid;

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


@RestController
@RequestMapping("/produto")
@Validated
public class ProdutoController {
  
    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        Produto obj = produtoService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> findAll() {
        List<Produto> produtos = produtoService.findAll();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<Produto>> findByMarcaId(@PathVariable Long marcaId) {
        List<Produto> produtos = produtoService.findByMarcaId(marcaId);
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Produto obj) {
        Produto savedProduto = produtoService.create(obj);
        URI uri = URI.create("/produto/" + savedProduto.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Produto obj, @PathVariable Long id) {
        obj.setId(id);
        obj = this.produtoService.update(id, obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
