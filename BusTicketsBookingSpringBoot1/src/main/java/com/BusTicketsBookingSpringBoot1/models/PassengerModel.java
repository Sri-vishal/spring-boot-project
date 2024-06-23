package com.BusTicketsBookingSpringBoot1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;




@Entity
@Table(name = "passenger")
@Data
public class PassengerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "number_passenger")
    private int numberPassenger;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "form_city")
    private String fromCity;
    @Column(name = "to_city")
    private String toCity;
    @Column(name = "number_of_seat")
    private int numberOfSeat;

    public PassengerModel(String name, String lastName, String fromCity, String toCity) {
        this.name = name;
        this.lastName = lastName;
        this.fromCity = fromCity;
        this.toCity = toCity;
    }

    public PassengerModel(PassengerModel passengerModel) {
        this.id = passengerModel.getId();
        this.numberPassenger = passengerModel.numberPassenger;
        this.name = passengerModel.getName();
        this.lastName = passengerModel.getLastName();
        this.fromCity = passengerModel.getFromCity();
        this.toCity = passengerModel.getToCity();
        this.numberOfSeat = passengerModel.getNumberOfSeat();
    }

    public PassengerModel() {
    }

}
