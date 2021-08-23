package br.com.hebert.citymanager.infrastructure.batch.processor;

import br.com.hebert.citymanager.domain.model.City;
import org.springframework.batch.item.ItemProcessor;


public class CityProcessor implements ItemProcessor<City, City> {

    // This method transforms data form one form to another.
    @Override
    public City process(final City city) throws Exception {
        return city;
    }
}
