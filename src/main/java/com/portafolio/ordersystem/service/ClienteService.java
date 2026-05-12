package com.portafolio.ordersystem.service;

import com.portafolio.ordersystem.dto.ClienteCreateDTO;
import com.portafolio.ordersystem.dto.ClienteDTO;
import com.portafolio.ordersystem.entity.Cliente;
import com.portafolio.ordersystem.dto.PedidoDTO;
import com.portafolio.ordersystem.exception.DuplicateResourceException;
import com.portafolio.ordersystem.exception.ResourceNotFoundException;
import com.portafolio.ordersystem.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Slf4j
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // ========== MÉTODOS PÚBLICOS ==========

    public Page<ClienteDTO> listarPaginado(Pageable pageable) {
        log.info("Listando clientes paginados");
                return clienteRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    public ClienteDTO guardar(ClienteCreateDTO dto) {
        log.info("Creando cliente con email: {}", dto.getEmail());

        // Validar email único
        validateEmailUnique(dto.getEmail());

        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());

        Cliente guardado = clienteRepository.save(cliente);
        log.info("Cliente creado con ID: {}", guardado.getId());

        return mapToDTO(guardado);
    }

    public ClienteDTO buscarPorId(Long id) {
        log.info("Buscando cliente ID: {}", id);

        Cliente cliente = clienteRepository.findByIdWithPedidos(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado con ID: " + id
                ));

        return mapToDTO(cliente);
    }

    public ClienteDTO actualizar(Long id, ClienteCreateDTO dto) {
        log.info("Actualizando cliente ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado con ID: " + id
                ));

        // Validar email único si cambió
        if (!cliente.getEmail().equals(dto.getEmail())) {
            validateEmailUnique(dto.getEmail());
        }

        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());

        Cliente actualizado = clienteRepository.save(cliente);
        log.info("Cliente actualizado ID: {}", actualizado.getId());

        return mapToDTO(actualizado);
    }

    public void eliminar(Long id) {
        log.info("Eliminando cliente ID: {}", id);

        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Cliente no encontrado con ID: " + id
            );
        }

        clienteRepository.deleteById(id);
        log.info("Cliente eliminado ID: {}", id);
    }

    // ========== MÉTODOS PRIVADOS ==========

    private ClienteDTO mapToDTO(Cliente cliente) {
        List<PedidoDTO> pedidosDto = null;

        if (cliente.getPedidos() != null) {
            pedidosDto = cliente.getPedidos().stream()
                    .map(p -> new PedidoDTO(
                            p.getId(),
                            p.getEstado(),
                            p.getTotal(),
                            p.getFecha(),
                            cliente.getId()
                    ))
                    .toList();
        }

        return new ClienteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                pedidosDto
        );


    }

    private void validateEmailUnique(String email) {
        if (clienteRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(
                    "Ya existe un cliente con el email: " + email
            );
        }
    }
    }
