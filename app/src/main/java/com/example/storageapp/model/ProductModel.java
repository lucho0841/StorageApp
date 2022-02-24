package com.example.storageapp.model;

public class ProductModel {
    int image;
    String nombre;
    String codigo;
    String precio;

    public ProductModel(int image, String nombre, String codigo, String precio) {
        this.image = image;
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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
