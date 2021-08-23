package br.com.hebert.citymanager.api.controller;

import br.com.hebert.citymanager.api.converter.CityConverter;
import br.com.hebert.citymanager.domain.dto.CityDTO;
import br.com.hebert.citymanager.api.response.DataResponse;
import br.com.hebert.citymanager.domain.business.CityBusiness;
import br.com.hebert.citymanager.domain.model.City;
import br.com.hebert.citymanager.infrastructure.enums.StatusResponseEnum;
import br.com.hebert.citymanager.infrastructure.exceptions.DuplicateKeyException;
import br.com.hebert.citymanager.infrastructure.exceptions.NullValueException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // From Angular default port for example
@RestController
@RequestMapping("v1/cities")
@Api(description = "Api's to deal with city crud operations")
public class CityCrudController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityCrudController.class);
    
    private final CityBusiness business;
    private final CityConverter converter;
    
    public CityCrudController(CityBusiness business, CityConverter converter) {
    	this.business = business;
    	this.converter = converter;
    }
    
	@ApiOperation(value = "Return all cities from the persistence")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource queried successfully"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Unknown error")})
	@RequestMapping(method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<DataResponse<List<CityDTO>>> findAll() {
		LOGGER.info("List all cities");
		return new ResponseEntity<>(new DataResponse<>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), this.converter.convertToDTOs(business.findAll())), HttpStatus.OK);
    }

	@ApiOperation(value = "Find a city by his id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource found successfully"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Unknown error")})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse<CityDTO>> findById(@PathVariable(value = "id") Long id) {
		LOGGER.info("Find: id={}", id);
		return business.findById(id)
				.map(entity ->{
					DataResponse<CityDTO> response = new DataResponse<>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), this.converter.convertToDTO(entity));
					return new ResponseEntity<>(response, HttpStatus.OK);
				}).orElse(new ResponseEntity<>(new DataResponse<>(HttpStatus.NOT_FOUND.value(), StatusResponseEnum.WARNING.getLabel()), HttpStatus.NOT_FOUND));
    }

	@ApiOperation(value = "Create a city")
	@ApiResponses(value = {
			@ApiResponse(code = 203, message = "Resource created successfully"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Unknown error")})
	@RequestMapping(method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse<CityDTO>> create(@RequestBody CityDTO newCity)  {
		LOGGER.info("Create: {}", newCity);
		try {
			City city = this.converter.convertToEntity(newCity);
			City savedCity = business.save(city);
			LOGGER.info("Created the City: {}", city);
			return new ResponseEntity<>(new DataResponse<>(HttpStatus.CREATED.value(), StatusResponseEnum.SUCCESS.getLabel(), this.converter.convertToDTO(savedCity)), HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateKeyException("City name '" + newCity.getName() + "' already exists! - " + e.getMessage());
		} catch (TransactionSystemException e) {
			throw new NullValueException("City has some value as NULL! - " + e);
		}
	}

	@ApiOperation(value = "Update a city by his id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource updated successfully"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Unknown error")})
	@RequestMapping(value = "/{id}", method =  RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse<CityDTO>> update(@PathVariable(value = "id") Long id, @RequestBody CityDTO newCity) {
		LOGGER.info("Update: id={}", id);
		City city = this.converter.convertToEntity(newCity);
		return business.findById(id)
				.map(entity ->{
					entity.setName(city.getName());
					entity.setIbgeId(city.getIbgeId());
					entity.setCapital(city.getCapital());
					entity.setAlternativeNames(city.getAlternativeNames());
					entity.setLon(city.getLon());
					entity.setLat(city.getLat());
					entity.setMesoRegion(city.getMesoRegion());
					entity.setMicroRegion(city.getMicroRegion());
					City updated = business.update(entity);
					DataResponse<CityDTO> response = new DataResponse<>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), this.converter.convertToDTO(updated));
					return new ResponseEntity<>(response, HttpStatus.OK);
				}).orElse(new ResponseEntity<>(new DataResponse<>(HttpStatus.NOT_FOUND.value(), StatusResponseEnum.WARNING.getLabel()), HttpStatus.NOT_FOUND));
	}

	@ApiOperation(value = "Delete a city by his id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource deleted successfully"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Unknown error")})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse<CityDTO>> delete(@PathVariable(value = "id") Long id) {
		LOGGER.info("Delete: id={}", id);
		return business.findById(id)
				.map(entity -> {
					business.delete(entity);
					DataResponse<CityDTO> response = new DataResponse<>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), this.converter.convertToDTO(entity));
					return new ResponseEntity<>(response, HttpStatus.OK);
				}).orElse(new ResponseEntity<>(new DataResponse<>(HttpStatus.NOT_FOUND.value(), StatusResponseEnum.WARNING.getLabel()), HttpStatus.NOT_FOUND));
		}
 
}