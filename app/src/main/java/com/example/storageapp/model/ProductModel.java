package com.example.storageapp.model;

import android.net.Uri;

public class ProductModel {
    int productoId;
    String image;
    String nombre;
    String codigo;
    String precio;
    int cantidad;
    String descripcion;
    String usuarioId;
    Boolean inactive;

    public ProductModel(int productoId, String image, String nombre, String codigo, String precio, int cantidad, String descripcion, String usuarioId, Boolean inactive) {
        this.productoId = productoId;
        this.image = image;
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.usuarioId = usuarioId;
        this.inactive = inactive;
    }



    public int getCantidad() {
        return cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
