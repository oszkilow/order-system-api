package com.portafolio.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PedidoDTO {
    private Long id;
    private String estado;
    private BigDecimal total;
    private LocalDateTime fecha; // La fecha que genera @CreatedDate o @PrePersist
    private Long clienteId;     // Solo el ID, no
}
