package com.apirest.apiadmin.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cardDetails")
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

    public CardDetailsModel(String number, String name, String securityCode, String expirationDate) {
        this.number = number;
        this.name = name;
        this.securityCode = securityCode;
        this.expirationDate = expirationDate;
    }

    public CardDetailsModel(){}

    public Long getIdCardDetails() {
        return idCardDetails;
    }

    public void setIdCardDetails(Long idCardDetails) {
        this.idCardDetails = idCardDetails;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
