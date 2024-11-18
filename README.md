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
    "idProducto": "producto 1",
    "precio": ""
  }, 
    {
    "idProducto": "producto 2",
    "precio": ""
  }]
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