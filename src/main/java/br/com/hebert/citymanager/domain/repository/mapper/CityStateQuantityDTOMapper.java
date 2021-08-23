package br.com.hebert.citymanager.domain.repository.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.hebert.citymanager.domain.dto.CityStateQuantityDTO;
import org.springframework.jdbc.core.RowMapper;

public class CityStateQuantityDTOMapper implements RowMapper<CityStateQuantityDTO> {

	@Override
	public CityStateQuantityDTO mapRow(ResultSet row, int rowNum) throws SQLException {
		return new CityStateQuantityDTO(row.getString("uf"), new BigDecimal(row.getString("quant")));
	}

}