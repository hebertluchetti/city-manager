package br.com.hebert.citymanager.domain.business;

import br.com.hebert.citymanager.domain.dto.*;
import br.com.hebert.citymanager.infrastructure.enums.CityColumnEnum;
import br.com.hebert.citymanager.infrastructure.exceptions.BadRequestException;
import br.com.hebert.citymanager.infrastructure.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CityInfoBusiness {
    List<CityStateQuantityDTO> findStateSmallestAndBiggestByCities() throws ResourceNotFoundException;
    List<CityStateQuantityDTO> findCityQuantityByState() throws ResourceNotFoundException;
    List<CityStateNameDTO> findCityNameByState(String filterState) throws ResourceNotFoundException, BadRequestException;
    List<CityDTO> searchValueInAllColumns(String searchValue) throws ResourceNotFoundException, BadRequestException;
    List<CityDistanceDTO> findBiggestDistance() throws ResourceNotFoundException;
    ColumnCountDTO countRegistersByColumnName(CityColumnEnum columnName) throws ResourceNotFoundException, BadRequestException;
}
