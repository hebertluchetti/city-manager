package br.com.hebert.citymanager.domain.repository;

import br.com.hebert.citymanager.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByIbgeId(Long ibgeId);

    long count();

    @Query(value =
            "SELECT c " +
            " FROM City c " +
            "WHERE c.capital=1 " +
            "ORDER BY c.name")
    List<City> findCapitals();
}
