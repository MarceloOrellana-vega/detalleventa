package com.api.spring.boot.detalleventa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar estadísticas de detalles de venta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasDTO {
    private long totalDetalles;
    private double totalVentas;
    private double promedioPorDetalle;
} 