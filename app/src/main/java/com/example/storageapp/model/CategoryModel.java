package com.example.storageapp.model;

public class CategoryModel {

    int codigoId;
    String codigo;
    String nombre;
    String descripcion;

    public CategoryModel(String codigo, String nombre, String descripcion, int codigoId) {
        this.codigoId = codigoId;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
