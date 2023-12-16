package com.example.ApiShop.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = Carrinho.TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Carrinho {
    public static final String TABLE_NAME = "Carrinho";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "total", scale = 2)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "foramPagamento_id", nullable = false)
    private FormaDePagamento formaDePagamento;


    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens;

    public void recalculateTotal() {
        if (itens != null && !itens.isEmpty()) {
            BigDecimal novoTotal = BigDecimal.ZERO;
    
            for (ItemCarrinho item : itens) {
                BigDecimal precoProduto = item.getProduto().getPreco();
                BigDecimal subtotalItem = precoProduto.multiply(BigDecimal.valueOf(item.getQuantidade()));
                novoTotal = novoTotal.add(subtotalItem);
            }
    
            setTotal(novoTotal);
        } else {
            setTotal(BigDecimal.ZERO);
        }
    }
}
