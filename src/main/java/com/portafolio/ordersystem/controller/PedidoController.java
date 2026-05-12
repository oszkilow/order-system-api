package com.portafolio.ordersystem.controller;

import com.portafolio.ordersystem.dto.PedidoCreateDTO;
import com.portafolio.ordersystem.dto.PedidoDTO;
import com.portafolio.ordersystem.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Gestión de pedidos de clientes")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar una nueva compra")
    public PedidoDTO crear(@Valid @RequestBody PedidoCreateDTO dto) {
        return pedidoService.crearPedido(dto);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar todos los pedidos de un cliente específico")
    public List<PedidoDTO> listarPorCliente(@PathVariable Long clienteId) {
        return pedidoService.listarPorCliente(clienteId);
    }
}