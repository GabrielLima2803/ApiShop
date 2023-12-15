package com.example.ApiShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ApiShop.model.Marca;
import com.example.ApiShop.repositories.MarcaRepository;

@Service  
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional(readOnly = true)
    public Marca findById(Long id) {
        Optional<Marca> marca = this.marcaRepository.findById(id);
        return marca.orElseThrow(() -> new RuntimeException(
                "Marca não encontrado! Id: " + id + ", Tipo: " + Marca.class.getName()));
    }
    
    @Transactional(readOnly = true)
    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }

    @Transactional
    public Marca create(Marca obj) {
        return this.marcaRepository.save(obj);
    }

    @Transactional
    public Marca update(Long id, Marca updatedMarca) {
        Marca existingMarca = findById(id);
        existingMarca.setNome(updatedMarca.getNome());
        return marcaRepository.save(existingMarca);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.marcaRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
        }
    }
}
