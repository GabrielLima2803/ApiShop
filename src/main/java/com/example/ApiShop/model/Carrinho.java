package com.example.ApiShop.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Carrinho.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode
public class Carrinho {
    public static final String TABLE_NAME = "Carrinho";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "total", scale = 2)
    @JsonProperty("total")
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "formaPagamento_id", nullable = false)  
    @JsonProperty("formaDePagamento")
    private FormaDePagamento formaDePagamento;

    @JsonProperty("itens")
    @OneToMany(mappedBy = "carrinho", fetch = FetchType.EAGER)
    private List<ItemCarrinho> itens;

    public void recalculateTotal() {
        if (itens != null && !itens.isEmpty()) {
            BigDecimal novoTotal = BigDecimal.ZERO;
    
            for (ItemCarrinho item : itens) {
                if (item.getProduto() != null && item.getProduto().getPreco() != null) {
                    BigDecimal precoProduto = item.getProduto().getPreco();
                    BigDecimal subtotalItem = precoProduto.multiply(BigDecimal.valueOf(item.getQuantidade()));
                    novoTotal = novoTotal.add(subtotalItem);
                }
            }
    
            setTotal(novoTotal);
        } else {
            setTotal(BigDecimal.ZERO);
        }
    }
}
