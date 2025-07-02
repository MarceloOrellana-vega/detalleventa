package com.api.spring.boot.detalleventa.controller;

import com.api.spring.boot.detalleventa.dto.EstadisticasDTO;
import com.api.spring.boot.detalleventa.model.DetalleVenta;
import com.api.spring.boot.detalleventa.service.DetalleVentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/detalles")
@Tag(name = "Detalle Venta", description = "API para gestionar detalles de ventas")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService service;

    @GetMapping
    @Operation(summary = "Listar todos los detalles de venta", description = "Obtiene una lista de todos los detalles de venta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CollectionModel<EntityModel<DetalleVenta>>> listar() {
        List<EntityModel<DetalleVenta>> detalles = service.listar().stream()
            .map(detalle -> EntityModel.of(detalle,
                linkTo(methodOn(DetalleVentaController.class).obtener(detalle.getIdDetalle())).withSelfRel(),
                linkTo(methodOn(DetalleVentaController.class).listar()).withRel("detalles"),
                linkTo(methodOn(DetalleVentaController.class).listarPorVenta(detalle.getIdVenta())).withRel("detalles-venta")
            ))
            .collect(Collectors.toList());

        CollectionModel<EntityModel<DetalleVenta>> collectionModel = CollectionModel.of(detalles);
        collectionModel.add(linkTo(methodOn(DetalleVentaController.class).listar()).withSelfRel());
        
        // Agregar link al API Gateway
        collectionModel.add(Link.of("http://localhost:8888", "api-gateway"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo detalle de venta", description = "Crea un nuevo detalle de venta. El campo idVenta debe corresponder a una venta existente en el microservicio de ventas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Detalle creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<DetalleVenta>> crear(
            @Parameter(description = "Detalle de venta a crear. El campo idVenta debe corresponder a una venta existente en el microservicio de ventas.") @RequestBody DetalleVenta detalle) {
        DetalleVenta detalleCreado = service.guardar(detalle);
        
        EntityModel<DetalleVenta> entityModel = EntityModel.of(detalleCreado,
            linkTo(methodOn(DetalleVentaController.class).obtener(detalleCreado.getIdDetalle())).withSelfRel(),
            linkTo(methodOn(DetalleVentaController.class).listar()).withRel("detalles"),
            linkTo(methodOn(DetalleVentaController.class).listarPorVenta(detalleCreado.getIdVenta())).withRel("detalles-venta")
        );
        
        // Agregar link al API Gateway
        entityModel.add(Link.of("http://localhost:8888", "api-gateway"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle por ID", description = "Obtiene un detalle de venta específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<DetalleVenta>> obtener(
            @Parameter(description = "ID del detalle de venta") @PathVariable Integer id) {
        DetalleVenta detalle = service.obtenerPorId(id);
        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }
        
        EntityModel<DetalleVenta> entityModel = EntityModel.of(detalle,
            linkTo(methodOn(DetalleVentaController.class).obtener(id)).withSelfRel(),
            linkTo(methodOn(DetalleVentaController.class).listar()).withRel("detalles"),
            linkTo(methodOn(DetalleVentaController.class).listarPorVenta(detalle.getIdVenta())).withRel("detalles-venta")
        );
        
        // Agregar link al API Gateway
        entityModel.add(Link.of("http://localhost:8888", "api-gateway"));
        
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar detalle de venta", description = "Actualiza un detalle de venta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<DetalleVenta>> actualizar(
            @Parameter(description = "ID del detalle de venta") @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del detalle") @RequestBody DetalleVenta detalle) {
        DetalleVenta detalleExistente = service.obtenerPorId(id);
        if (detalleExistente == null) {
            return ResponseEntity.notFound().build();
        }
        
        detalle.setIdDetalle(id);
        DetalleVenta detalleActualizado = service.guardar(detalle);
        
        EntityModel<DetalleVenta> entityModel = EntityModel.of(detalleActualizado,
            linkTo(methodOn(DetalleVentaController.class).obtener(id)).withSelfRel(),
            linkTo(methodOn(DetalleVentaController.class).listar()).withRel("detalles"),
            linkTo(methodOn(DetalleVentaController.class).listarPorVenta(detalleActualizado.getIdVenta())).withRel("detalles-venta")
        );
        
        // Agregar link al API Gateway
        entityModel.add(Link.of("http://localhost:8888", "api-gateway"));
        
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar detalle de venta", description = "Elimina un detalle de venta por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Detalle eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del detalle de venta a eliminar") @PathVariable Integer id) {
        DetalleVenta detalle = service.obtenerPorId(id);
        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }
        
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/venta/{idVenta}")
    @Operation(summary = "Listar detalles por venta", description = "Obtiene todos los detalles de una venta específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CollectionModel<EntityModel<DetalleVenta>>> listarPorVenta(
            @Parameter(description = "ID de la venta") @PathVariable Integer idVenta) {
        List<EntityModel<DetalleVenta>> detalles = service.listarPorVenta(idVenta).stream()
            .map(detalle -> EntityModel.of(detalle,
                linkTo(methodOn(DetalleVentaController.class).obtener(detalle.getIdDetalle())).withSelfRel(),
                linkTo(methodOn(DetalleVentaController.class).listar()).withRel("detalles"),
                linkTo(methodOn(DetalleVentaController.class).listarPorVenta(idVenta)).withRel("detalles-venta")
            ))
            .collect(Collectors.toList());

        CollectionModel<EntityModel<DetalleVenta>> collectionModel = CollectionModel.of(detalles);
        collectionModel.add(linkTo(methodOn(DetalleVentaController.class).listarPorVenta(idVenta)).withSelfRel());
        collectionModel.add(linkTo(methodOn(DetalleVentaController.class).listar()).withRel("detalles"));
        
        // Agregar link al API Gateway
        collectionModel.add(Link.of("http://localhost:8888", "api-gateway"));
        
        return ResponseEntity.ok(collectionModel);
    }

    // Nuevo endpoint para obtener estadísticas básicas
    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas", description = "Obtiene estadísticas básicas de los detalles de venta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<EstadisticasDTO>> obtenerEstadisticas() {
        List<DetalleVenta> todosLosDetalles = service.listar();
        
        long totalDetalles = todosLosDetalles.size();
        double totalVentas = todosLosDetalles.stream()
            .mapToDouble(detalle -> detalle.getPrecioUnitario().doubleValue() * detalle.getCantidad())
            .sum();
        
        EstadisticasDTO stats = new EstadisticasDTO(
            totalDetalles,
            totalVentas,
            totalDetalles > 0 ? totalVentas / totalDetalles : 0
        );
        
        EntityModel<EstadisticasDTO> entityModel = EntityModel.of(stats,
            linkTo(methodOn(DetalleVentaController.class).obtenerEstadisticas()).withSelfRel(),
            linkTo(methodOn(DetalleVentaController.class).listar()).withRel("detalles")
        );
        
        // Agregar link al API Gateway
        entityModel.add(Link.of("http://localhost:8888", "api-gateway"));
        
        return ResponseEntity.ok(entityModel);
    }
}
