package com.apirest.apiadmin.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name = "descuentos")
public class DescuentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDescuento;

    @Column
    private String descripcion;

    @Column
    private String porcentajeDescuento;

    @Column
    private Date fechaCaducidad;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_gerente")
    private GerenteModel gerente;

    @Column
    private LocalDateTime dateEvent;

    @Column
    private String Operation;

    public DescuentoModel(){}

    public DescuentoModel(String descripcion, String porcentajeDescuento, Date fechaCaducidad, GerenteModel gerente){
        this.descripcion = descripcion;
        this.porcentajeDescuento = porcentajeDescuento;
        this.fechaCaducidad = fechaCaducidad;
        this.gerente = gerente;
    }


    public boolean isValid() {
        Instant instant = Instant.now();
        ZoneId z = ZoneId.of( "America/Argentina/Tucuman" );
        ZonedDateTime zdt = instant.atZone(z);

        if (zdt.isAfter(fechaCaducidad.toInstant().atZone(z))){
            return false;
        }
        return true;
    }

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

    public Integer getIdDescuento() {
        return idDescuento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public GerenteModel getGerente() {
        return gerente;
    }

    public void setIdDescuento(Integer idDescuento) {
        this.idDescuento = idDescuento;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPorcentajeDescuento(String porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public void setGerente(GerenteModel gerente) {
        this.gerente = gerente;
    }
}
