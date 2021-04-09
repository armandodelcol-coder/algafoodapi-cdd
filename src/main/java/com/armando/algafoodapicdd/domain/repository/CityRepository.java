package com.armando.algafoodapicdd.domain.repository;

import com.armando.algafoodapicdd.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    public City getByNameAndStateId(String name, Long stateId);

    @Query("from City city join fetch city.state")
    public List<City> findAll();

}
