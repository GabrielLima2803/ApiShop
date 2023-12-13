package com.example.ApiShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ApiShop.model.Produto;
import com.example.ApiShop.repositories.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public Produto findById(Long id) {
        Optional<Produto> produto = this.produtoRepository.findById(id);
        return produto.orElseThrow(() -> new RuntimeException(
                "Produto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()
        ));
    }

    @Transactional(readOnly = true)
    public List<Produto> findByMarcaId(Long marcaId) {
        return produtoRepository.findByMarca_Id(marcaId);
    }

    @Transactional
    public Produto create(Produto obj) {
        return produtoRepository.save(obj);
    }

    @Transactional(readOnly = true)
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Transactional
    public Produto update(Long id, Produto updatedProduto) {
        Produto existingProduto = findById(id);

        existingProduto.setNome(updatedProduto.getNome());
        existingProduto.setDescricao(updatedProduto.getDescricao());
        existingProduto.setImg(updatedProduto.getImg());
        existingProduto.setPreco(updatedProduto.getPreco());
        existingProduto.setMarca(updatedProduto.getMarca());

        return produtoRepository.save(existingProduto);
    }

    public void delete(Long id){
        findById(id);
        try {
            this.produtoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possivel excluir pois há entidades relacionadas!");
        }
    }
}
