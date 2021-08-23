package br.com.hebert.citymanager.infrastructure.converter;

import java.util.List;
import java.util.stream.Collectors;

import br.com.hebert.citymanager.domain.dto.GenericDTO;
import br.com.hebert.citymanager.domain.model.GenericEntity;

public interface Converter<DTO extends GenericDTO, ENT extends GenericEntity> {
    public DTO convertToDTO(ENT entity);
    public ENT convertToEntity(DTO dto);    
    
    public default List<DTO> convertToDTOs(List<ENT> entities) {
        return entities.stream().map(entity->convertToDTO(entity)).collect(Collectors.toList());
    }
    public default List<ENT> converterToEntities(List<DTO> dtos) {
        return dtos.stream().map(dto->convertToEntity(dto)).collect(Collectors.toList());
    }

}
