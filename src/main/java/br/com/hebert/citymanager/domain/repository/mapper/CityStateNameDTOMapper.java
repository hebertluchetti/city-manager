package br.com.hebert.citymanager.domain.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.hebert.citymanager.domain.dto.CityStateNameDTO;
import org.springframework.jdbc.core.RowMapper;

public class CityStateNameDTOMapper implements RowMapper<CityStateNameDTO> {

	@Override
	public CityStateNameDTO mapRow(ResultSet row, int rowNum) throws SQLException {
		return new CityStateNameDTO(row.getString("uf"), row.getString("name"));
	}

}