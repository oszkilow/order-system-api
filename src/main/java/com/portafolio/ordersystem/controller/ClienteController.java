package com.portafolio.ordersystem.controller;

import com.portafolio.ordersystem.dto.ClienteCreateDTO;
import com.portafolio.ordersystem.dto.ClienteDTO;
import com.portafolio.ordersystem.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Clientes", description = "API para gestión de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar clientes con paginación",
            description = "Obtiene una lista paginada de clientes. Puedes usar parámetros como page, size y sort.")
    public Page<ClienteDTO> listar(
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        log.info("GET /api/clientes - Página: {}, Tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return clienteService.listarPaginado(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ClienteDTO buscarPorId(@PathVariable Long id) {
        log.info("GET /api/clientes/{}", id);
        return clienteService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nuevo cliente",
            description = "Registra un nuevo cliente validando que el email sea único y tenga formato correcto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos de entrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto: El email ya está registrado")
    })
    public ClienteDTO guardar(@Valid @RequestBody ClienteCreateDTO dto) {
        log.info("POST /api/clientes - Intentando crear cliente con email: {}", dto.getEmail());
        return clienteService.guardar(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado para actualizar"),
            @ApiResponse(responseCode = "409", description = "El nuevo email ya pertenece a otro cliente")
    })
    public ClienteDTO actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteCreateDTO dto) {
        log.info("PUT /api/clientes/{}", id);
        return clienteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public void eliminar(@PathVariable Long id) {
        log.info("DELETE /api/clientes/{}", id);
        clienteService.eliminar(id);
    }
}