package com.example.demo;

public class Empleado {
    private String nombre;
    private String apellidos;
    private int edad;
    private String departamento;
    private String puesto;

    public Empleado(String nombre, String apellidos, int edad, String departamento, String puesto) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.departamento = departamento;
        this.puesto = puesto;
    }

    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public int getEdad() { return edad; }
    public String getDepartamento() { return departamento; }
    public String getPuesto() { return puesto; }
}
