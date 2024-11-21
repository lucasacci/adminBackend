# Ventas
### Crear venta

POST
Path: /ventas/create

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

Response:
tiene un error cuando lo arregle lo cambio

# Servicios
### Alta Servicios

GET
Path: /productos

Response:
```json
[
    {
        "id_producto": 1,
        "nombre": "Un programa",
        "precio": 30000.0,
        "stock": 0,
        "categoria": "hola",
        "imagen": null
    },
    {
        "id_producto": 2,
        "nombre": "Un programa 2",
        "precio": 40000.0,
        "stock": 0,
        "categoria": "hola",
        "imagen": null
    }
]
```

GET
Path: /productos/:id

Response:
```json
{
    "id_producto": 1,
    "nombre": "Un programa",
    "precio": 30000.0,
    "stock": 0,
    "categoria": "hola",
    "imagen": null
}
```

POST
Path: /productos/crearproducto

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

Response: //Se cambiara a un json
```json
Producto creado con exito.
```


# Vendedores
### Alta Vendedores

GET
Path: /vendedores

Response: //esto se cambiara a que devuelva como json
```json
[
    {
        "id_vendedor": 1,
        "nombre": "Jeremias",
        "apellido": "Moreno Ivanoff",
        "correo": "jeremiasivanoff@hotmail.es",
        "dni": 42939904,
        "id_gerente": null,
        "ventas": [
            {}
        ]
    }
]
```

GET
Path: /vendedores/:id

Response:
```json
{
    "id_vendedor": 1,
    "nombre": "Jeremias",
    "apellido": "Moreno Ivanoff",
    "correo": "jeremiasivanoff@hotmail.es",
    "dni": 42939904,
    "id_gerente": null,
    "ventas": [
        {}
    ]
}
```

POST
Path: /vendedores/crearvendedor

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

Response: //Esto se va cambiar a un json
```json
Vendedor creado con exito.
```

# Gerentes
### Alta Gerentes

POST:
Path: /gerentes/add

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

Response ej:
```json
{
    "status": 200,// AHora recibe este status, pero lo voy a sacar
    "message": "Gerente Registrado Correctamente.",
    "data": {}
}
```

GET
Path: /gerentes

Response ej: //Este tengo que corregirlo para que lo devuelva en json
```json
[
    {
        "id_gerente": 1,
        "dni": "42939901",
        "nombre": "Jeremias",
        "apellido": "Moreno Ivanoff",
        "email": "jeremiasivanoff@hotmail.es",
        "direccion": "avenida Belgrano 1779"
    },
    {
        "id_gerente": 2,
        "dni": "42939900",
        "nombre": "Jeremias",
        "apellido": "Moreno Ivanoff",
        "email": "jeremiasivanoff@hotmail.es",
        "direccion": "avenida Belgrano 1779"
    },
    {
        "id_gerente": 3,
        "dni": "42939904",
        "nombre": "Jeremias",
        "apellido": "Moreno Ivanoff",
        "email": "jeremiasivanoff@hotmail.es",
        "direccion": "avenida Belgrano 1779"
    }
]
```

GET
Path: /gerentes/:id

Response ej:
```json
{
    "id_gerente": 1,
    "dni": "42939901",
    "nombre": "Jeremias",
    "apellido": "Moreno Ivanoff",
    "email": "jeremiasivanoff@hotmail.es",
    "direccion": "avenida Belgrano 1779"
}
```

# Clientes
### Alta Clientes

Path: /clientes

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

### Buscar Descuentos

Path: /descuentos?onlyValid={true or false}
