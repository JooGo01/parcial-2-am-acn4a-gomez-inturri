package com.example.for2pay.model;

public class Event {
    Double precio;
    String nombre, descripcion;
    Long dia_pago;

    public Event(){}

    public Event(Double precio, String nombre, String descripcion, Long dia_pago) {
        this.precio = precio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dia_pago = dia_pago;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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

    public Long getDia_pago() {
        return dia_pago;
    }

    public void setDia_pago(Long dia_pago) {
        this.dia_pago = dia_pago;
    }
}
