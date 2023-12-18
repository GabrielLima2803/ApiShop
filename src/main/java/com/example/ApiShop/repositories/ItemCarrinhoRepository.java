package com.example.ApiShop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ApiShop.model.Carrinho;
import com.example.ApiShop.model.ItemCarrinho;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
        List<ItemCarrinho> findByCarrinho(Carrinho carrinho);

        @Query("SELECT i FROM ItemCarrinho i WHERE i.carrinho = :carrinho AND i.produto.id = :produtoId")
        Optional<ItemCarrinho> findByCarrinhoAndProdutoId(Carrinho carrinho, Long produtoId);

}
