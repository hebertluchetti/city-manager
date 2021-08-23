package br.com.hebert.citymanager.api.converter;


import br.com.hebert.citymanager.domain.dto.CityDTO;
import br.com.hebert.citymanager.domain.model.City;
import br.com.hebert.citymanager.infrastructure.converter.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component 
public class CityConverter implements Converter<CityDTO, City> {
    private ModelMapper modelMapper;

    public CityConverter(ModelMapper modelMapper) {
    	this.modelMapper = modelMapper;
    }

	public CityDTO convertToDTO(City entity) {
		return modelMapper.map(entity, CityDTO.class);
	}
	public City convertToEntity(CityDTO dto) {
		return modelMapper.map(dto, City.class);
	}

}
