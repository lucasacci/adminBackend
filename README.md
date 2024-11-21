# Ventas

### POST
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
    "idProducto": "id"
  }, 
    {
    "idProducto": "id"
  }]
}
```

Response:
```json
{
  "message": "Venta Generada exitosamente",
  "data": null
}
```

# Servicios

### GET
Path: /productos

Response:
```json
{
  "message": "Listado Productos.",
  "data": [
    {
      "id_producto": 1,
      "nombre": "Un programa 1",
      "precio": "40000.0",
      "categoria": "hola",
      "comentarios": "Tiempo de vida: 2 anios",
      "id_gerente": 1
    },
    {
      "id_producto": 2,
      "nombre": "Un programa 2",
      "precio": "40000.0",
      "categoria": "hola",
      "comentarios": "Tiempo de vida: 3 anios",
      "id_gerente": 1
    }
  ]
}
```

### GET
Path: /productos/:id

Response:
```json
{
  "id_producto": 1,
  "nombre": "Un programa 2",
  "precio": 40000.0,
  "stock": 0,
  "categoria": "hola",
  "imagen": null,
  "gerente": {
    "id_gerente": 1,
    "dni": "42949906",
    "nombre": "Jeremias",
    "apellido": "Moreno Ivanoff",
    "email": "jeremiasivanoff@hotmail.es",
    "direccion": "avenida Belgrano 1779",
    "dateEvent": "2024-11-21T19:31:22.350062",
    "operation": "INSERT"
  },
  "dateEvent": "2024-11-21T19:33:08.115576",
  "operation": "INSERT"
}
```

### POST
Path: /productos/crearproducto

Body:
```json
{
  "categoria": "hola",
  "nombre": "Un programa 1",
  "precio": 40000,
  "comentarios": "Tiempo de vida: 2 anios",
  "id_gerente": "1"
}
```

Response:
```json
{
  "message": "Producto generado exitosmaente.",
  "data": null
}
```


# Vendedores

### GET
Path: /vendedores

Response: //esto se cambiara a que devuelva como json
```json
[
  {
    "id_vendedor": 1,
    "nombre": "Jeremias",
    "apellido": "Moreno Ivanoff",
    "correo": "jeremiasivanoff@hotmail.es",
    "dni": 41949302,
    "direccion": "avenida an4sdfo 3423479",
    "id_gerente": {
      "id_gerente": 1,
      "dni": "42949906",
      "nombre": "Jeremias",
      "apellido": "Moreno Ivanoff",
      "email": "jeremiasivanoff@hotmail.es",
      "direccion": "avengrano 89779",
      "dateEvent": "2024-11-21T19:47:46.743386",
      "operation": "INSERT"
    },
    "ventas": [],
    "dateEvent": "2024-11-21T19:53:26.069321",
    "operation": "INSERT"
  },
  {
    "id_vendedor": 2,
    "nombre": "Esteban",
    "apellido": "sanchez",
    "correo": "joff@hotmail.es",
    "dni": 0,
    "direccion": "rivadavia 79",
    "id_gerente": {
      "id_gerente": 1,
      "dni": "42949906",
      "nombre": "Jeremias",
      "apellido": "Moreno Ivanoff",
      "email": "jeremiasivanoff@hotmail.es",
      "direccion": "avengrano 89779",
      "dateEvent": "2024-11-21T19:47:46.743386",
      "operation": "INSERT"
    },
    "ventas": [],
    "dateEvent": "2024-11-21T19:55:09.894283",
    "operation": "INSERT"
  }
]
```

### GET
Path: /vendedores/:id

Response:
```json
{
  "id_vendedor": 1,
  "nombre": "Jeremias",
  "apellido": "Moreno Ivanoff",
  "correo": "jeremiasivanoff@hotmail.es",
  "dni": 41949302,
  "direccion": "avenida an4sdfo 3423479",
  "id_gerente": {
    "id_gerente": 1,
    "dni": "42949906",
    "nombre": "Jeremias",
    "apellido": "Moreno Ivanoff",
    "email": "jeremiasivanoff@hotmail.es",
    "direccion": "avengrano 89779",
    "dateEvent": "2024-11-21T19:47:46.743386",
    "operation": "INSERT"
  },
  "ventas": [],
  "dateEvent": "2024-11-21T19:53:26.069321",
  "operation": "INSERT"
}
```

### POST
Path: /vendedores/crearvendedor

Body:
```json
{
  "dni": "",
  "nombre": "",
  "apellido": "",
  "email": "",
  "direccion": "",
  "id_gerente": ""
}
```

Response:
```json
{
  "message": "Vendedor registrador exitosamente.",
  "data": null
}
```

# Gerentes

### POST:
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
  "message": "Gerente Registrado Correctamente.",
  "data": {}
}
```

### GET
Path: /gerentes

Response ej: //Este tengo que corregirlo para que lo devuelva en json
```json
[
  {
    "id_gerente": 1,
    "dni": "42949906",
    "nombre": "Jeremias",
    "apellido": "Moreno Ivanoff",
    "email": "jeremiasivanoff@hotmail.es",
    "direccion": "avenida Be3123179",
    "dateEvent": "2024-11-21T19:47:46.743386",
    "operation": "INSERT"
  },
  {
    "id_gerente": 2,
    "dni": "42942329906",
    "nombre": "Jeremias",
    "apellido": "Moreno Ivanoff",
    "email": "jeremiasivanoff@hotmail.es",
    "direccion": "avenida Be6756 79",
    "dateEvent": "2024-11-21T19:57:38.228956",
    "operation": "INSERT"
  }
]
```

### GET
Path: /gerentes/:id

Response ej:
```json
{
  "id_gerente": 2,
  "dni": "42942329906",
  "nombre": "Jeremias",
  "apellido": "Moreno Ivanoff",
  "email": "jeremiasivanoff@hotmail.es",
  "direccion": "avenida Be6756 79",
  "dateEvent": "2024-11-21T19:57:38.228956",
  "operation": "INSERT"
}
```

# Clientes

### GET
Path: /clientes

Response: //Este tengo que corregirlo para que lo devuelva en json y quitaria que venga toda la info del vendedor, que directamente ponga el id nomas
```json
[
    {
        "id_cliente": 1,
        "nombre": "Jeremias",
        "apellido": "Moreno Ivanoff",
        "email": "jeremiasivanoff@hotmail.es",
        "dni": 429323902,
        "ventas": [],
        "vendedor": {
          "id_vendedor": 1,
          "nombre": "Jeremias",
          "apellido": "Moreno Ivanoff",
          "correo": "jeremiasivanoff@hotmail.es",
          "dni": 41949302,
          "direccion": "avenida an4sdfo 3423479",
          "id_gerente": {
            "id_gerente": 1,
            "dni": "42949906",
            "nombre": "Jeremias",
            "apellido": "Moreno Ivanoff",
            "email": "jeremiasivanoff@hotmail.es",
            "direccion": "avengrano 89779",
            "dateEvent": "2024-11-21T19:47:46.743386",
            "operation": "INSERT"
          },
          "ventas": [],
          "dateEvent": "2024-11-21T19:53:26.069321",
          "operation": "INSERT"
        },
        "dateEvent": "2024-11-21T20:01:03.850616",
        "operation": "INSERT"
    },
    {
        "id_cliente": 2,
        "nombre": "Esteban",
        "apellido": "Rocha",
        "email": "jereasdassivanoff@hotmail.es",
        "dni": 429312312,
        "ventas": [],
        "vendedor":{
          "id_vendedor": 1,
          "nombre": "Jeremias",
          "apellido": "Moreno Ivanoff",
          "correo": "jeremiasivanoff@hotmail.es",
          "dni": 41949302,
          "direccion": "avenida an4sdfo 3423479",
          "id_gerente": {
            "id_gerente": 1,
            "dni": "42949906",
            "nombre": "Jeremias",
            "apellido": "Moreno Ivanoff",
            "email": "jeremiasivanoff@hotmail.es",
            "direccion": "avengrano 89779",
            "dateEvent": "2024-11-21T19:47:46.743386",
            "operation": "INSERT"
          },
          "ventas": [],
          "dateEvent": "2024-11-21T19:53:26.069321",
          "operation": "INSERT"
        },
        "dateEvent": "2024-11-21T20:02:07.749219",
        "operation": "INSERT"
    }
]
```

### GET
Path: /clientes/:id

Response: //Este tengo que corregirlo para que lo devuelva en json y quitaria que venga toda la info del vendedor, que directamente ponga el id nomas
```json
{
    "id_producto": 1,
    "nombre": "Un programa 1",
    "precio": 40000.0,
    "stock": 0,
    "categoria": "hola",
    "comentarios": "Tiempo de vida: 2 anios",
    "imagen": null,
    "gerente": {
      "id_gerente": 1,
      "dni": "42949906",
      "nombre": "Jeremias",
      "apellido": "Moreno Ivanoff",
      "email": "jeremiasivanoff@hotmail.es",
      "direccion": "avenida Be3123179",
      "dateEvent": "2024-11-21T19:47:46.743386",
      "operation": "INSERT"
    },
    "dateEvent": "2024-11-21T19:47:51.320708",
    "operation": "INSERT"
}
```

### POST
Path: /clientes

Body:
```json
{
  "dni": "429323902",
  "nombre": "Jeremias",
  "apellido": "Moreno Ivanoff",
  "email": "jeremiasivanoff@hotmail.es",
  "direccion": "avenida Be2312779",
  "idVendedor": "1"
}
```

Response:
```json
{
    "message": "Cliente Registrado Correctamente.",
    "data": null
}
```

# Descuentos

### POST
Path: /descuentos/add/:idGerente

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
