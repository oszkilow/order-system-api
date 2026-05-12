package com.portafolio.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;//
import lombok.EqualsAndHashCode;

import java.math.BigDecimal; // Recomendado para dinero

@Entity
@Data
@Table(name = "pedidos")
//@EqualsAndHashCode(callSuper = true)
public class Pedido  {///extends Auditable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estado;
    private BigDecimal total;

    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @PrePersist
    protected void onCreate() {
        if (this.fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}



