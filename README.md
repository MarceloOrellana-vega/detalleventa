# API de Detalle de Venta - Perfunlandia

API REST que gestiona los detalles asociados a una venta en el sistema de Perfunlandia, como productos vendidos, cantidades y montos. Esta API está conectada con la API de `ventas` y es consumida por la API de `reportes`.

---

## 🛠️ Tecnologías utilizadas

- Java 17
- Spring Boot 3.4.x
- Maven
- MySQL
- Swagger / OpenAPI 3
- HATEOAS

---

## ⚙️ Configuración

- **Puerto local:** `8082`
- **Swagger UI:** [`http://localhost:8082/swagger-ui.html`](http://localhost:8082/swagger-ui.html)

---

## 📌 Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/detalleventas` | Listar todos los detalles de venta |
| `GET` | `/detalleventas/{id}` | Obtener detalle por ID |
| `POST` | `/detalleventas` | Crear nuevo detalle |
| `PUT` | `/detalleventas/{id}` | Actualizar detalle existente |
| `DELETE` | `/detalleventas/{id}` | Eliminar un detalle |

---

## 🔗 Conexión con otras APIs

- **Consume:** `ventas` (para obtener datos de venta)
- **Es consumida por:** `reportes` (para construir reportes integrados)

---

## 🧪 Pruebas

- Puedes probar los endpoints desde Postman o directamente desde Swagger.
