package com.armando.algafoodapicdd.domain.repository;

import com.armando.algafoodapicdd.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    public City getByNameAndStateId(String name, Long stateId);

}
