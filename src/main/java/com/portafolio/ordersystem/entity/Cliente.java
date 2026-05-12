package com.portafolio.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cliente extends  Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    // Relación Uno a Muchos: Un cliente tiene muchos pedidos
    // mappedBy: indica que el campo "cliente" en la clase Pedido es el dueño de la relación
    // cascade: si borras al cliente, se borran sus pedidos (opcional, según lógica de negocio)
    // orphanRemoval: elimina pedidos de la BD si se quitan de esta lista
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Esto evita el bucle infinito al responder el JSON
    private List<Pedido> pedidos = new ArrayList<>(); // IMPORTANTE: Inicializar siempre la lista

    // Helper method para agregar pedidos (buena práctica en JPA)
    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
        pedido.setCliente(this);
    }
}
