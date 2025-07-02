package com.api.spring.boot.detalleventa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Detalle de venta. El campo idVenta debe corresponder a una venta existente en el microservicio de ventas.
 */
@Entity
@Table(name = "detalle_boleta")
@Data
@EqualsAndHashCode(callSuper = false)
public class DetalleVenta extends RepresentationModel<DetalleVenta> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @Schema(description = "ID de la venta a la que pertenece el detalle. Debe existir en el microservicio de ventas.", example = "1001", required = true)
    @Column(name = "id_venta")
    private Integer idVenta;

    @Column(name = "id_producto")
    private Integer idProducto;

    private Integer cantidad;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

}