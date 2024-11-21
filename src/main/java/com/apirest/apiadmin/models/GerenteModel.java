package com.apirest.apiadmin.models;

import com.apirest.apiadmin.listeners.AuditGerenteModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "gerentes")
@EntityListeners(AuditGerenteModel.class)
public class GerenteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id_gerente;

    @Column
    private String dni;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String email;

    @Column
    private String direccion;

    @Column
    private LocalDateTime dateEvent;

    @Column
    private String Operation;

    public GerenteModel(String dni, String nombre, String apellido, String email, String direccion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.direccion = direccion;
    }

    public GerenteModel(){}

    @PrePersist
    public void onPrePresist(){
        audit("INSERT");
    }

    @PreUpdate
    public void onPreUpdate(){
        audit("UPDATE");
    }

    public void audit(String operation){
        setOperation(operation);
        setDateEvent(LocalDateTime.now());
    }

    public LocalDateTime getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(LocalDateTime dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public long getId_gerente() {
        return id_gerente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setId_gerente(long id_gerente) {
        this.id_gerente = id_gerente;
    }
}
