package br.com.hebert.citymanager.api.controller;

import br.com.hebert.citymanager.api.converter.CityConverter;
import br.com.hebert.citymanager.domain.dto.*;
import br.com.hebert.citymanager.api.response.DataResponse;
import br.com.hebert.citymanager.domain.business.CityBusiness;
import br.com.hebert.citymanager.domain.business.CityInfoBusiness;
import br.com.hebert.citymanager.domain.model.City;
import br.com.hebert.citymanager.infrastructure.enums.CityColumnEnum;
import br.com.hebert.citymanager.infrastructure.enums.StatusResponseEnum;
import br.com.hebert.citymanager.infrastructure.exceptions.BadRequestException;
import br.com.hebert.citymanager.infrastructure.exceptions.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // From Angular default port for instance
@RestController
@RequestMapping("v1/info/cities")
@Api(description = "Api's to get city information from the persistence")
public class CityInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityCrudController.class);

    private CityBusiness business;
    private CityInfoBusiness cityInfoBusiness;
    private CityConverter converter;

    public CityInfoController(CityInfoBusiness cityInfoBusiness, CityBusiness business, CityConverter converter) {
        this.business = business;
        this.converter = converter;
        this.cityInfoBusiness = cityInfoBusiness;
    }

    @ApiOperation(value = "Total number of cities")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource queried successfully"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unknown error")})
    @RequestMapping(value = "/quantity", method =  RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<Long>>findCityQuantity() {
        LOGGER.info("Get number of cities by state...");
        Long len = business.findCityQuantity();
        LOGGER.info("Number of cities by state is : {}", len);
        DataResponse<Long> response =  new DataResponse<Long>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), len);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Number of cities by state")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource queried successfully"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unknown error")})
    @RequestMapping(value = "/quantity-by-state", method =  RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<List<CityStateQuantityDTO>>>findCityQuantityByState() throws ResourceNotFoundException {
        LOGGER.info("Number of cities by state ...");
        List<CityStateQuantityDTO> dtos = cityInfoBusiness.findCityQuantityByState();
        LOGGER.info("Number of cities by state finished");
        DataResponse<List<CityStateQuantityDTO>> response = new DataResponse<List<CityStateQuantityDTO>>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), dtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Capital cities ordered by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource queried successfully"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unknown error")})
    @RequestMapping(value = "/capitals", method =  RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<List<CityDTO>>>capitals() {
        LOGGER.info("Capital cities ordered by name ...");
        List<City> capitals = business.findCapitals();
        LOGGER.info("Capital Cities quantity : {}", capitals.size());
        List<CityDTO> dtos = this.converter.convertToDTOs(capitals);
        DataResponse<List<CityDTO>> response = new DataResponse<List<CityDTO>>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), dtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Number of cities by state - smallest and biggest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource queried successfully"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unknown error")})
    @RequestMapping(value = "/minmax-by-state", method =  RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<List<CityStateQuantityDTO>>>findStateSmallestAndBiggestByCities() throws ResourceNotFoundException {
        LOGGER.info("Number of cities from state (smallest and biggest quantity) ...");
        List<CityStateQuantityDTO> dtos = cityInfoBusiness.findStateSmallestAndBiggestByCities();
        LOGGER.info("State with the smallest amount of cities : {} - State with the biggest number of cities: {}", dtos.get(0), dtos.get(1));
        DataResponse<List<CityStateQuantityDTO>> response = new DataResponse<List<CityStateQuantityDTO>>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), dtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Find a city by his IBGE ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource found successfully"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unknown error")})
    @RequestMapping(value = "/ibge/{ibgeId}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<CityDTO>> findByIbgeId(@PathVariable(value = "ibgeId") Long ibgeId) {
        LOGGER.info("Find: ibgeId={}", ibgeId);
        return business.findByIbgeId(ibgeId)
                .map(entity ->{
                    DataResponse<CityDTO> response = new DataResponse<CityDTO>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), this.converter.convertToDTO(entity));
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>( new DataResponse<CityDTO>(HttpStatus.NOT_FOUND.value(), StatusResponseEnum.WARNING.getLabel()), HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Name of cities by state")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource queried successfully"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unknown error")})
    @RequestMapping(value = "/{state}", method =  RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<List<CityStateNameDTO>>>findCityNameByState(@PathVariable(value = "state") String state) throws ResourceNotFoundException, BadRequestException {
        LOGGER.info("Name of cities by state : {}", state);
        List<CityStateNameDTO> dtos = cityInfoBusiness.findCityNameByState(state);
        LOGGER.info("Name of cities by state {} finished", state);
        DataResponse<List<CityStateNameDTO>> response = new DataResponse<List<CityStateNameDTO>>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), dtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Search value into all columns")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource queried successfully"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unknown error")})
    @RequestMapping(value = "/search/{value}", method =  RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<List<CityDTO>>>searchValueInAllColumns(@PathVariable(value = "value") String value) throws ResourceNotFoundException, BadRequestException {
        LOGGER.info("Search value {} into all column", value);
        List<CityDTO> dtos = cityInfoBusiness.searchValueInAllColumns(value);
        LOGGER.info("Search value {} into all column finished", value);
        DataResponse<List<CityDTO>> response = new DataResponse<List<CityDTO>>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), dtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Find biggest distance between the cities in KMs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource queried successfully"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unknown error")})
    @RequestMapping(value = "/biggest-distance", method =  RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<CityDistanceDTO>>findBiggestDistance() throws ResourceNotFoundException {
        LOGGER.info("Find biggest distance between the cities in kms) ...");
        List<CityDistanceDTO> dtos = cityInfoBusiness.findBiggestDistance();
        CityDistanceDTO dto = dtos.get(0);
        LOGGER.info(" The biggest distance between the cities is : {} ", dto);
        DataResponse<CityDistanceDTO> response = new DataResponse<CityDistanceDTO>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "The count of registers by column name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource queried successfully"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unknown error")})
    @RequestMapping(value = "/count-by-column/{column}", method =  RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<ColumnCountDTO>>countRegistersByColumnName(@PathVariable(value = "column") CityColumnEnum column) throws BadRequestException, ResourceNotFoundException {
        LOGGER.info("The count of registers by column name {}", column);
        ColumnCountDTO dto = cityInfoBusiness.countRegistersByColumnName(column);
        LOGGER.info("The count of registers by column name {} is {}", column, dto);
        DataResponse<ColumnCountDTO> response =  new DataResponse<ColumnCountDTO>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
