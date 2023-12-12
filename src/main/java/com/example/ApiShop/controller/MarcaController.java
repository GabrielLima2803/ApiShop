package com.example.ApiShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

import com.example.ApiShop.model.Marca;
import com.example.ApiShop.service.MarcaService;

@RestController
@RequestMapping("/marca")
@Validated
public class MarcaController {
    @Autowired
    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> findById(@PathVariable Long id) {
        Marca obj = marcaService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping
    public ResponseEntity<List<Marca>> findAll() {
        List<Marca> marcas = marcaService.findAll();
        return ResponseEntity.ok(marcas);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Marca obj) {
        Marca savedMarca = marcaService.create(obj);
        URI uri = URI.create("/marca/" + savedMarca.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Marca obj, @PathVariable Long id) {
        obj.setId(id);
        obj = this.marcaService.update(id, obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marcaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
