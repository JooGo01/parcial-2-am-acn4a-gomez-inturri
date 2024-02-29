package com.example.for2pay.model;

public class Event {
    Double precio;
    String nombre, descripcion;
    int dia;

public Event(){}

    public Event(Double precio, String nombre, String descripcion, int dia) {
        this.precio = precio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dia = dia;
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

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}
