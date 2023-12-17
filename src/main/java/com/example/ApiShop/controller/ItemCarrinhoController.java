package com.example.ApiShop.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.example.ApiShop.model.ItemCarrinho;
import com.example.ApiShop.service.ItemCarrinhoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@Validated
@RequestMapping("/itemcarrinho")

public class ItemCarrinhoController {
    private static final Logger logger = LoggerFactory.getLogger(ItemCarrinhoService.class);

    @Autowired
    private ItemCarrinhoService itemCarrinhoService ;

    @GetMapping("/{id}")
    public ResponseEntity<ItemCarrinho> findById(@PathVariable Long id) {
        ItemCarrinho obj = itemCarrinhoService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping
    public ResponseEntity<List<ItemCarrinho>> findAll() {
        logger.info("Fetching all ItemCarrinho");
        List<ItemCarrinho> itemCarrinhos = itemCarrinhoService.findAll();
        return ResponseEntity.ok(itemCarrinhos);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ItemCarrinho obj) {
        ItemCarrinho savedItemCarrinho = itemCarrinhoService.create(obj);
        URI uri = URI.create("/itemcarrinho/" + savedItemCarrinho.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ItemCarrinho obj, @PathVariable Long id) {
        obj.setId(id);
        obj = this.itemCarrinhoService.update(id, obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemCarrinhoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
