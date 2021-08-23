package br.com.hebert.citymanager.domain.business;

import br.com.hebert.citymanager.domain.model.City;

import java.util.List;
import java.util.Optional;

public interface CityBusiness extends GenericBusiness<City,Long> {

    Optional<City> findByIbgeId(Long ibgeId);
    Long findCityQuantity();
    public List<City> findCapitals();

}
