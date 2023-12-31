package com.example.ApiShop.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.ApiShop.model.Carrinho;
import com.example.ApiShop.model.ItemCarrinho;
import com.example.ApiShop.model.Produto;
import com.example.ApiShop.service.CarrinhoService;
import com.example.ApiShop.service.ProdutoService;



@RestController
@RequestMapping("/carrinho")
@Validated
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Carrinho>> getAllCarrinhos() {
        List<Carrinho> carrinhos = carrinhoService.findAll();
        return new ResponseEntity<>(carrinhos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrinho> getCarrinhoById(@PathVariable Long id) {
        Carrinho carrinho = carrinhoService.findById(id);
        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Carrinho> createCarrinho() {
        Carrinho novoCarrinho = carrinhoService.create(new Carrinho());
        return new ResponseEntity<>(novoCarrinho, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrinho> updateCarrinho(@PathVariable Long id, @RequestBody Carrinho updatedCarrinho) {
        Carrinho carrinho = carrinhoService.update(id, updatedCarrinho);
        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrinho(@PathVariable Long id) {
        carrinhoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/adicionar-item")
    public ResponseEntity<Carrinho> adicionarItem(@PathVariable Long id, @RequestBody ItemCarrinho itemCarrinho) {
    Carrinho carrinho = carrinhoService.findById(id);    
    Produto produto = produtoService.findById(itemCarrinho.getProduto().getId());
    itemCarrinho.setProduto(produto);
    
    carrinhoService.adicionarItem(carrinho, itemCarrinho);
    
    return new ResponseEntity<>(carrinho, HttpStatus.OK);
}


@PostMapping("/{id}/remover-item")
public ResponseEntity<Carrinho> removerItem(@PathVariable Long id, @RequestBody Map<String, Long> requestBody) {
    Long produtoId = requestBody.get("produtoId");
    Carrinho carrinho = carrinhoService.findById(id);
    carrinhoService.removerItem(carrinho, produtoId);
    return new ResponseEntity<>(carrinho, HttpStatus.OK);
}

@PostMapping("/{id}/add-quantidade")
public ResponseEntity<Carrinho> adicionarOuAtualizarQuantidade(
        @PathVariable Long id, @RequestBody ItemCarrinho itemCarrinho) {
    Carrinho carrinho = carrinhoService.findById(id);
    Produto produto = produtoService.findById(itemCarrinho.getProduto().getId());
    itemCarrinho.setProduto(produto);

    carrinhoService.adicionarOuAtualizarQuantidade(carrinho, itemCarrinho);

    return new ResponseEntity<>(carrinho, HttpStatus.OK);
}

@PostMapping("/{id}/remove-quantidade")
public ResponseEntity<Carrinho> removerQuantidade(
        @PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
    try {
        Long produtoId = ((Number) requestBody.get("produtoId")).longValue();
        Integer quantidade = ((Number) requestBody.get("quantidade")).intValue();

        Carrinho carrinho = carrinhoService.findById(id);
        carrinhoService.removerQuantidade(carrinho, produtoId, quantidade);

        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    } catch (ClassCastException | NullPointerException | ArithmeticException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


}