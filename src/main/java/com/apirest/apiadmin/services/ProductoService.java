package com.apirest.apiadmin.services;

import com.apirest.apiadmin.models.ClientModel;
import com.apirest.apiadmin.models.ProductoModel;
import com.apirest.apiadmin.repositories.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    IProductoRepository productoRepository;

    public ProductoModel guardarProducto(ProductoModel producto) {
        return productoRepository.save(producto);
    }

    public List<ProductoModel> getProductos(){
        return new ArrayList<>(productoRepository.findAll());
    }

    public ProductoModel getProducto(Integer id){
        return this.productoRepository.findById(id).get();
    }

    public ProductoModel editProducto(ProductoModel producto, Integer id){
        ProductoModel productoEditado = this.productoRepository.findById(id).get();

        productoEditado.setNombre(producto.getNombre());
        productoEditado.setCategoria(producto.getCategoria());
        productoEditado.setPrecio(producto.getPrecio());
        productoEditado.setStock(producto.getStock());
        //productoEditado.setImagen(producto.getImagen());
        //productoEditado.setCantidad(producto.getCantidad());

       return productoRepository.save(productoEditado);
    }

    public boolean eliminarProducto(Integer id){
        try {
            if (!productoRepository.existsById(id)) {
                return false;
            }
            productoRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
