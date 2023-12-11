package com.example.ApiShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ApiShop.model.Favorito;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    
}
