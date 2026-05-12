package com.portafolio.ordersystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PedidoCreateDTO {

    @NotBlank(message = "El estado es obligatorio (ej. PENDIENTE, COMPLETADO)")
    private String estado;

    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a cero")
    private BigDecimal total;

    @NotNull(message = "El ID del cliente es obligatorio para asociar el pedido")
    private Long clienteId;
}
