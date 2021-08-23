package br.com.hebert.citymanager.domain.business.impl;

import br.com.hebert.citymanager.domain.dto.*;
import br.com.hebert.citymanager.domain.business.CityInfoBusiness;
import br.com.hebert.citymanager.domain.repository.CityInfoRepository;
import br.com.hebert.citymanager.infrastructure.enums.CityColumnEnum;
import br.com.hebert.citymanager.infrastructure.exceptions.BadRequestException;
import br.com.hebert.citymanager.infrastructure.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CityInfoBusinessImpl implements CityInfoBusiness {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityInfoBusinessImpl.class);

	private CityInfoRepository repository;
	public CityInfoBusinessImpl(CityInfoRepository repository) {
		this.repository = repository;	
	}

	public List<CityStateQuantityDTO> findStateSmallestAndBiggestByCities() throws ResourceNotFoundException {
		try {
			List<String> excludedStates = Arrays.asList("DF");
			return repository.findStateSmallestAndBiggestByCities(excludedStates);
		} catch (Exception e) {
			LOGGER.error("No registries found", e);
			throw new ResourceNotFoundException("No registries found");
		}
	}

	public List<CityStateQuantityDTO> findCityQuantityByState() throws ResourceNotFoundException {
		try {
			List<String> excludedStates = Arrays.asList("DF");
			List<CityStateQuantityDTO> dtos = repository.findCityQuantityByState(excludedStates);
			if (Objects.isNull(dtos) || dtos.isEmpty()) {
				LOGGER.error("No registries found");
				throw new ResourceNotFoundException("No registries found");
			}
			return dtos;
		} catch (Exception e) {
			LOGGER.error("No registries found", e);
			throw new ResourceNotFoundException("No registries found");
		}
	}

	public List<CityStateNameDTO> findCityNameByState(String filterState) throws ResourceNotFoundException, BadRequestException {
		List<CityStateNameDTO> dtos = null;
		if (Objects.isNull(filterState) || filterState.isBlank()) {
			LOGGER.error("Invalid state value: {}",filterState );
			throw new BadRequestException("Invalid state value : "+filterState);
		}
		filterState = filterState.toUpperCase(Locale.ROOT);

		try {
			List<String> filterStates = Arrays.asList(filterState);
			dtos = repository.findCityNameByState(filterStates);
		} catch (Exception e) {
			LOGGER.error("No registries found", e);
			throw new ResourceNotFoundException("No registries found");
		}

		if (Objects.isNull(dtos) || dtos.isEmpty()) {
			LOGGER.error("No registries found");
			throw new ResourceNotFoundException("No registries found");
		}
		return dtos;
	}

	public List<CityDTO> searchValueInAllColumns(String searchValue) throws ResourceNotFoundException, BadRequestException {
		List<CityDTO> dtos = null;
		if (Objects.isNull(searchValue) || searchValue.isBlank()) {
			LOGGER.error("Invalid search value: {}",searchValue );
			throw new BadRequestException("Invalid search value : "+searchValue);
		}

		try {
			dtos = repository.searchValueInAllColumns(searchValue);
		} catch (Exception e) {
			LOGGER.error("No registries found", e);
			throw new ResourceNotFoundException("No registries found");
		}

		if (Objects.isNull(dtos) || dtos.isEmpty()) {
			LOGGER.error("No registries found");
			throw new ResourceNotFoundException("No registries found");
		}
		return dtos;
	}

	public List<CityDistanceDTO> findBiggestDistance() throws ResourceNotFoundException {
		try {
			List<CityDistanceDTO> dtos = repository.findBiggestDistance();
			if (Objects.isNull(dtos) || dtos.isEmpty()) {
				LOGGER.error("No registries found");
				throw new ResourceNotFoundException("No registries found");
			}
			return dtos;
		} catch (Exception e) {
			LOGGER.error("No registries found", e);
			throw new ResourceNotFoundException("No registries found");
		}
	}

	public ColumnCountDTO countRegistersByColumnName(CityColumnEnum columnName) throws ResourceNotFoundException, BadRequestException {
		ColumnCountDTO dto = null;
		if (Objects.isNull(columnName) || columnName.getColumn().isBlank()) {
			LOGGER.error("Invalid column name: {}", columnName );
			throw new BadRequestException("Invalid  column name : "+columnName);
		}

		try {
			dto = repository.countRegistersByColumnName(columnName.getColumn());
		} catch ( IllegalArgumentException e) {
			LOGGER.error("Invalid column name", e);
			throw new BadRequestException("Invalid column name");
		} catch (Exception e) {
			LOGGER.error("No registries found", e);
			throw new ResourceNotFoundException("No registries found");
		}

		if (Objects.isNull(dto)) {
			LOGGER.error("No registries found");
			throw new ResourceNotFoundException("No registries found");
		}
		return dto;
	}

}
