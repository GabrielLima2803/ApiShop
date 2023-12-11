package com.example.ApiShop.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(name = "total_da_compra", precision = 6, scale = 2)
    private BigDecimal totalDaCompra; 
    
    @ManyToOne
    @JoinColumn(name = "foramPagamento_id", nullable = false, updatable = false)
    private FormaDePagamento formaDePagamento;
}
