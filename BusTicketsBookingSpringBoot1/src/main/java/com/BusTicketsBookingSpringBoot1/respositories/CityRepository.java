package com.BusTicketsBookingSpringBoot1.respositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.BusTicketsBookingSpringBoot1.models.CityModel;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<CityModel, Integer> {
    List<CityModel> findAll();
}
