package br.com.hebert.citymanager.domain.business;

import br.com.hebert.citymanager.infrastructure.exceptions.BadRequestException;

public interface CityCsvBusiness {
    String importCityCsv() throws BadRequestException;
}
