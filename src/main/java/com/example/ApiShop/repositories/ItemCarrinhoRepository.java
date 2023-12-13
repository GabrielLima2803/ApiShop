package com.example.ApiShop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ApiShop.model.Carrinho;
import com.example.ApiShop.model.ItemCarrinho;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
        List<ItemCarrinho> findByCarrinho(Carrinho carrinho);

}
