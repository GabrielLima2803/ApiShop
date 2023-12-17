package com.example.ApiShop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ApiShop.model.Carrinho;
import com.example.ApiShop.repositories.CarrinhoRepository;

@Service
public class CarrinhoService {
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Transactional(readOnly = true)
    public Carrinho findById(Long id) {
        return carrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado! Id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Carrinho> findAll() {
        return carrinhoRepository.findAll();
    }

    @Transactional
    public Carrinho create(Carrinho obj) {
        return carrinhoRepository.save(obj);
    }

    @Transactional
    public Carrinho update(Long id, Carrinho updatedCarrinho) {
        Carrinho existingCarrinho = findById(id);
        existingCarrinho.setTotal(updatedCarrinho.getTotal());
        existingCarrinho.setFormaDePagamento(updatedCarrinho.getFormaDePagamento());
        existingCarrinho.setItens(updatedCarrinho.getItens());
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

        


}
