package com.example.ApiShop.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ApiShop.model.Carrinho;
import com.example.ApiShop.model.ItemCarrinho;
import com.example.ApiShop.repositories.CarrinhoRepository;
import com.example.ApiShop.repositories.ItemCarrinhoRepository;

@Service
public class CarrinhoService {
    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Transactional(readOnly = true)
    public Carrinho findById(Long id) {
        Optional<Carrinho> carrinho = this.carrinhoRepository.findById(id);
        return carrinho.orElseThrow(() -> new RuntimeException(
                "Carrinho não encontrado! Id: " + id + ", Tipo: " + Carrinho.class.getName()));
    }

 @Transactional
public Carrinho create(Carrinho obj) {
    Carrinho carrinho = new Carrinho();
    carrinho.setTotal(BigDecimal.ZERO);
    carrinho.setItens(new ArrayList<>()); 
    return carrinhoRepository.save(carrinho);
}

    @Transactional(readOnly = true)
    public List<Carrinho> findAll() {
        return carrinhoRepository.findAll();
    }

    @Transactional
    public Carrinho update(Long id, Carrinho updatedCarrinho) {
        Carrinho existingCarrinho = findById(id);

        return carrinhoRepository.save(existingCarrinho);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.carrinhoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possivel excluir pois há entidades relacionadas!");
        }
    }

    public void adicionarItem(Carrinho carrinho, ItemCarrinho itemCarrinho) {
        itemCarrinho.setCarrinho(carrinho);
        itemCarrinhoRepository.save(itemCarrinho);

        atualizarTotal(carrinho);
    }

    public void removerItem(Carrinho carrinho, ItemCarrinho itemCarrinho) {
        itemCarrinho.setCarrinho(null);
        itemCarrinhoRepository.delete(itemCarrinho);

        atualizarTotal(carrinho);
    }

    public BigDecimal calcularTotal(List<ItemCarrinho> itensCarrinho) {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemCarrinho item : itensCarrinho) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    @Transactional
    public void atualizarTotal(Carrinho carrinho) {
        List<ItemCarrinho> itensCarrinho = itemCarrinhoRepository.findByCarrinho(carrinho);

        BigDecimal novoTotal = calcularTotal(itensCarrinho);

        carrinho.setTotal(novoTotal);

        carrinhoRepository.save(carrinho);
    }
}
