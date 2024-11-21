# Ventas
### Crear venta

Path: /ventas

Body:
```json
{
  "fechaVenta": "",
  "idVendedor": "",
  "idCliente": "",
  "montoTotal": "",
  "lineasDeVenta": [
    {
    "idProducto": "producto 1"
  }, 
    {
    "idProducto": "producto 2"
  }]
}
```

Response:
```json
{
    "status": 201, //este no deberia estar, lo sacaremos
    "message": "Venta Generada exitosamente",
    "data": null
}
```

# Servicios
### Alta Servicios

Path: /servicios

Body:
```json
{
  "nombre": "",
  "categoria": "",
  "precio": "",
  "idGerente": "",
  "tiempoFinSoporte": ""
}
```

# Vendedores
### Alta Vendedores

Path: /vendedores

Body:
```json
{
  "dni": "",
  "nombre": "",
  "apellido": "",
  "email": "",
  "direccion": "",
  "idGerente": ""
}
```

# Gerentes
### Alta Gerentes

Path: /gerentes

Body:
```json
{
  "dni": "",
  "nombre": "",
  "apellido": "",
  "email": "",
  "direccion": ""
}
```

# Descuentos
### Alta Descuento

Path: /descuentos

Body:
```json
{
  "descripcion": "",
  "porcentajeDescuento": "",
  "idGerente": "",
  "fechaCaducidad": ""
}
```