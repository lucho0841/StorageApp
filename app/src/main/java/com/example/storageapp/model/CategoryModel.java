package com.example.storageapp.model;

public class CategoryModel {

    int codigoId;
    String codigo;
    String nombre;
    String descripcion;
    String usuarioId;
    int categoriaId;

    public CategoryModel(String usuarioId, String codigo, String nombre, String descripcion, int codigoId) {
        this.usuarioId = usuarioId;
        this.codigoId = codigoId;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public CategoryModel(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public CategoryModel() {}

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setProductoId(int productoId) {
        this.categoriaId = productoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
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
