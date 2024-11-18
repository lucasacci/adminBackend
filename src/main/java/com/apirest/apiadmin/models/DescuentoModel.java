package com.apirest.apiadmin.models;

import jakarta.persistence.*;

import java.time.Instant;
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
}
