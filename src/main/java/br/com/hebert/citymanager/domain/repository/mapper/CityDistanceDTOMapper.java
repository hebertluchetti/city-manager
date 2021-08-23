package br.com.hebert.citymanager.domain.repository.mapper;

import br.com.hebert.citymanager.domain.dto.CityDistanceDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDistanceDTOMapper implements RowMapper<CityDistanceDTO> {

	@Override
	public CityDistanceDTO mapRow(ResultSet row, int rowNum) throws SQLException {
		return CityDistanceDTO.builder().
				fromCity(row.getString("from_city")).
				fromUF(row.getString("from_uf")).
				toCity(row.getString("to_city")).
				toUF(row.getString("to_uf")).
				distanceInKm(row.getBigDecimal("distance_in_km")).
				build();
	}
}