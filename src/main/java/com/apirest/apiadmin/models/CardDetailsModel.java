package com.apirest.apiadmin.models;

import jakarta.persistence.*;

@Entity
@Table("cardDetails")
public class CardDetailsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCardDetails;

    @Column
    private String number;

    @Column
    private String name;

    @Column
    private String securityCode;

    @Column
    private String expirationDate;


}
