package com.BusTicketsBookingSpringBoot1.respositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.BusTicketsBookingSpringBoot1.models.PassengerModel;

import java.util.List;

@Repository
public interface PassengerRespository extends CrudRepository<PassengerModel, Integer> {
    List<PassengerModel> findAll();
    void deleteAllByNumberPassenger(int number);

}
