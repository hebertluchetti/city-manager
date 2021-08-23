package br.com.hebert.citymanager.domain.business.impl;

import br.com.hebert.citymanager.domain.business.CityBusiness;
import br.com.hebert.citymanager.domain.model.City;
import br.com.hebert.citymanager.domain.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CityBusinessImpl implements CityBusiness {
	private static final long serialVersionUID = 8634361298043395424L;
	
	private CityRepository repository;

	public CityBusinessImpl(CityRepository repository) {
		this.repository = repository;	
	}

	@Override
	public List<City> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<City> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void delete(City entity) {
		repository.delete(entity);
	}

	@Override
	public City save(City entity) {
		return repository.save(entity);
	}
	
	@Override
	public City update(City entity) {
		return repository.save(entity);
	}

	public Optional<City> findByIbgeId(Long ibgeId) {
		Optional<City> found = repository.findByIbgeId(ibgeId);

		if (found.isPresent()) {
			return found;
		}
		return Optional.empty();
	}

	public Long findCityQuantity() {
		long len = repository.count();
		return Long.valueOf(len);
	}
	public List<City> findCapitals() {
		return repository.findCapitals();
	}

}
