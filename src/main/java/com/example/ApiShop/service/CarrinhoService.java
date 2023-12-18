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

    @Transactional
    public void adicionarItem(Carrinho carrinho, ItemCarrinho itemCarrinho) {
        List<ItemCarrinho> itensCarrinho = itemCarrinhoRepository.findByCarrinho(carrinho);

        Optional<ItemCarrinho> itemExistente = itensCarrinho.stream()
                .filter(item -> item.getProduto().equals(itemCarrinho.getProduto()))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrinho itemCarrinhoExistente = itemExistente.get();
            itemCarrinhoExistente.setQuantidade(itemCarrinhoExistente.getQuantidade() + itemCarrinho.getQuantidade());
        } else {
            itemCarrinho.setCarrinho(carrinho);
            itemCarrinhoRepository.save(itemCarrinho);
        }

        atualizarTotal(carrinho);
    }

    @Transactional
    public void removerItem(Carrinho carrinho, Long produtoId) {
        ItemCarrinho itemCarrinho = itemCarrinhoRepository.findByCarrinhoAndProdutoId(carrinho, produtoId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho!"));

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

    @Transactional
public void adicionarOuAtualizarQuantidade(Carrinho carrinho, ItemCarrinho itemCarrinho) {
    List<ItemCarrinho> itensCarrinho = itemCarrinhoRepository.findByCarrinho(carrinho);

    Optional<ItemCarrinho> itemExistente = itensCarrinho.stream()
            .filter(item -> item.getProduto().equals(itemCarrinho.getProduto()))
            .findFirst();

    if (itemExistente.isPresent()) {
        ItemCarrinho itemCarrinhoExistente = itemExistente.get();
        itemCarrinhoExistente.setQuantidade(itemCarrinho.getQuantidade());
    } else {
        itemCarrinho.setCarrinho(carrinho);
        itemCarrinhoRepository.save(itemCarrinho);
    }

    atualizarTotal(carrinho);
}

@Transactional
public void removerQuantidade(Carrinho carrinho, Long produtoId, int quantidade) {
    ItemCarrinho itemCarrinho = itemCarrinhoRepository.findByCarrinhoAndProdutoId(carrinho, produtoId)
            .orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho!"));

    int novaQuantidade = itemCarrinho.getQuantidade() - quantidade;

    if (novaQuantidade > 0) {
        itemCarrinho.setQuantidade(novaQuantidade);
    } else {
        itemCarrinho.setCarrinho(null);
        itemCarrinhoRepository.delete(itemCarrinho);
    }

    atualizarTotal(carrinho);
}

@Transactional
public void atualizarQuantidade(Carrinho carrinho, Long produtoId, int novaQuantidade) {
    ItemCarrinho itemCarrinho = itemCarrinhoRepository.findByCarrinhoAndProdutoId(carrinho, produtoId)
            .orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho!"));

    itemCarrinho.setQuantidade(novaQuantidade);

    atualizarTotal(carrinho);
}
}
