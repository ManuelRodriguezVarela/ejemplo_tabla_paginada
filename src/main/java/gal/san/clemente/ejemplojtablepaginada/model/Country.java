/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.san.clemente.ejemplojtablepaginada.model;

/**
 *
 * @author manu
 */
public class Country {
    private int id;
    private String country;
    private String city;
    private int numberInhabitants;

    public Country(int id, String country, String city, int numberInhabitants) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.numberInhabitants = numberInhabitants;
    }

    public Country() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumberInhabitants() {
        return numberInhabitants;
    }

    public void setNumberInhabitants(int numberInhabitants) {
        this.numberInhabitants = numberInhabitants;
    }
}
