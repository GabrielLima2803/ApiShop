package com.example.ApiShop.controller;

import com.example.ApiShop.model.Carrinho;
import com.example.ApiShop.service.CarrinhoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carrinho")
@Validated
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/{id}")
    public ResponseEntity<Carrinho> findById(@PathVariable Long id) {
        Carrinho obj = carrinhoService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Carrinho>> findAll() {
        List<Carrinho> carrinhos = carrinhoService.findAll();
        return ResponseEntity.ok(carrinhos);
    }
    @PostMapping("/criar")
    public ResponseEntity<Carrinho> create(@Valid @RequestBody Carrinho carrinho) {
        Carrinho savedCarriho = this.carrinhoService.create(carrinho);
        URI uri = URI.create("/carrinho/" + savedCarriho.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Carrinho obj, @PathVariable Long id) {
        obj.setId(id);
        obj = this.carrinhoService.update(id, obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carrinhoService.delete(id);
        return ResponseEntity.noContent().build(); 
    }


}