package com.example.ApiShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ApiShop.model.Carrinho;
import com.example.ApiShop.model.ItemCarrinho;
import com.example.ApiShop.repositories.ItemCarrinhoRepository;

@Service
public class ItemCarrinhoService {
    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Autowired 
    private CarrinhoService carrinhoService;

    @Transactional(readOnly = true)
    public ItemCarrinho findById(Long id) {
        Optional<ItemCarrinho> ItemCarrinho = this.itemCarrinhoRepository.findById(id);
        return ItemCarrinho.orElseThrow(() -> new RuntimeException(
                "ItemCarrinho não encontrado! Id: " + id + ", Tipo: " + ItemCarrinho.class.getName()));
    }
    
    @Transactional(readOnly = true)
    public List<ItemCarrinho> findAll() {
        return itemCarrinhoRepository.findAll();
    }

    @Transactional
    public ItemCarrinho create(ItemCarrinho obj) {
        ItemCarrinho newItemCarrinho = this.itemCarrinhoRepository.save(obj);
        Carrinho carrinho = newItemCarrinho.getCarrinho();
        
        carrinho.getItens().add(newItemCarrinho);
        carrinho.recalculateTotal(); 
    
        return newItemCarrinho;
    }
    @Transactional
    public ItemCarrinho update(Long id, ItemCarrinho updatedItemCarrinho) {
        ItemCarrinho existingItemCarrinho = findById(id);
        existingItemCarrinho.setProduto(updatedItemCarrinho.getProduto());
        return itemCarrinhoRepository.save(existingItemCarrinho);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.itemCarrinhoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
        }
    }
    @Transactional(readOnly = true)
    public List<ItemCarrinho> findByCarrinho(Carrinho carrinho) {
        return itemCarrinhoRepository.findByCarrinho(carrinho);
    }
}
