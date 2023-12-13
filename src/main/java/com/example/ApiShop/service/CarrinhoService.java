package com.example.ApiShop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.ApiShop.model.Carrinho;
import com.example.ApiShop.model.ItemCarrinho;
import com.example.ApiShop.repositories.CarrinhoRepository;

public class CarrinhoService {
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ItemCarrinhoService itemCarrinhoService;

    @Transactional(readOnly = true)
    public Carrinho findById(Long id) {
        return carrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado! Id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Carrinho> findAll() {
        return carrinhoRepository.findAll();
    }

    public Carrinho create(Carrinho obj) {
        return carrinhoRepository.save(obj);
    }

    public Carrinho update(Long id, Carrinho updatedCarrinho) {
        Carrinho existingCarrinho = findById(id);
        existingCarrinho.setFormaDePagamento(updatedCarrinho.getFormaDePagamento());
        return carrinhoRepository.save(existingCarrinho);
    }

    public void delete(Long id) {
        findById(id);
        try {
            carrinhoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

    @Transactional(readOnly = true)
    public List<ItemCarrinho> getItensCarrinho(Long carrinhoId) {
        Carrinho carrinho = findById(carrinhoId);
        return itemCarrinhoService.findByCarrinho(carrinho);
    }

    @Transactional
    public Carrinho adicionarItem(Long carrinhoId, Long itemId) {
        Carrinho carrinho = findById(carrinhoId);
        ItemCarrinho item = itemCarrinhoService.findById(itemId);
        List<ItemCarrinho> itens = carrinho.getItens();
        boolean itemExistente = false;

        for (ItemCarrinho i : itens) {
            if (i.getProduto().getId().equals(item.getProduto().getId())) {
                itemExistente = true;
                break;
            }
        }

        if (!itemExistente) {
            ItemCarrinho novoItem = new ItemCarrinho();
            novoItem.setProduto(item.getProduto());
            carrinho.getItens().add(novoItem);
        }

        return carrinhoRepository.save(carrinho);
    }

    @Transactional
    public Carrinho removerItem(Long carrinhoId, Long itemId) {
        Carrinho carrinho = findById(carrinhoId);
        ItemCarrinho item = itemCarrinhoService.findById(itemId);

        carrinho.getItens().removeIf(i -> i.getProduto().getId().equals(item.getProduto().getId()));

        return carrinhoRepository.save(carrinho);
    }
}
