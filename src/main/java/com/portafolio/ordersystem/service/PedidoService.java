package com.portafolio.ordersystem.service;

import com.portafolio.ordersystem.dto.PedidoCreateDTO;
import com.portafolio.ordersystem.dto.PedidoDTO;
import com.portafolio.ordersystem.entity.Cliente;
import com.portafolio.ordersystem.entity.Pedido;
import com.portafolio.ordersystem.exception.ResourceNotFoundException;
import com.portafolio.ordersystem.repository.ClienteRepository;
import com.portafolio.ordersystem.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public PedidoDTO crearPedido(PedidoCreateDTO dto) {
        log.info("Iniciando creación de pedido: Cliente ID {}, Total: {}", dto.getClienteId(), dto.getTotal());

        // 1. Validar que el cliente exista
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el cliente con ID: " + dto.getClienteId()));

        // 2. Mapear DTO a Entidad
        Pedido pedido = new Pedido();
        pedido.setEstado(dto.getEstado());
        pedido.setTotal(dto.getTotal());
        pedido.setCliente(cliente);

        // 3. Guardar
        Pedido guardado = pedidoRepository.save(pedido);
        log.info("Pedido guardado exitosamente con ID: {}", guardado.getId());

        return mapToDTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<PedidoDTO> listarPorCliente(Long clienteId) {
        log.info("Consultando historial de pedidos para el cliente: {}", clienteId);

        if (!clienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException("Cliente no encontrado con ID: " + clienteId);
        }

        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    private PedidoDTO mapToDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getEstado(),
                pedido.getTotal(),
                pedido.getFecha(),
                pedido.getCliente().getId()
        );
    }
}