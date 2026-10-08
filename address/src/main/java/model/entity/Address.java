package model.entity;

import enums.AddressType;
import jakarta.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long empld;
    private String street;
    private Long pinCode;
    private String city;
    private String country;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpld() {
        return empld;
    }

    public void setEmpld(Long empld) {
        this.empld = empld;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", empld=" + empld +
                ", street='" + street + '\'' +
                ", pinCode=" + pinCode +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", addressType=" + addressType +
                '}';
    }

    public Address(Long id, Long empld, String street, Long pinCode, String city, String country, AddressType addressType) {
        this.id = id;
        this.empld = empld;
        this.street = street;
        this.pinCode = pinCode;
        this.city = city;
        this.country = country;
        this.addressType = addressType;
    }

    public Address() {
    }
}
