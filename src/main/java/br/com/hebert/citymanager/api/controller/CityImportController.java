package br.com.hebert.citymanager.api.controller;

import br.com.hebert.citymanager.api.response.DataResponse;
import br.com.hebert.citymanager.domain.business.CityCsvBusiness;
import br.com.hebert.citymanager.infrastructure.enums.StatusResponseEnum;
import br.com.hebert.citymanager.infrastructure.exceptions.BadRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200") // From Angular default port for example
@RestController
@RequestMapping(path = "v1/import")
@Api(description="Api for importing file data into the persistence")
public class CityImportController {

    private CityCsvBusiness cityCsvBusiness;

    public CityImportController(CityCsvBusiness cityCsvBusiness) {
        this.cityCsvBusiness = cityCsvBusiness;
    }

    @GetMapping(path = "/cities")
    @ApiOperation(value = "Import a CSV file city data into the persistence")
    public ResponseEntity<DataResponse<String>> importCSVCity() throws BadRequestException {
        return new ResponseEntity<>(new DataResponse<String>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), cityCsvBusiness.importCityCsv()),HttpStatus.OK);
    }
}
