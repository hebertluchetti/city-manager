package br.com.hebert.citymanager.domain.repository.mapper;

import br.com.hebert.citymanager.domain.dto.CityDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDTOMapper implements RowMapper<CityDTO> {

	@Override
	public CityDTO mapRow(ResultSet row, int rowNum) throws SQLException {
		return  CityDTO.builder().
				id(row.getLong("id")).
				ibgeId(row.getLong("ibge_id")).
				uf(row.getString("uf")).
				name(row.getString("name")).
				alternativeNames(row.getString("alternative_names")).
				capital(row.getBoolean("capital")).
				lat(row.getBigDecimal("lat")).
				lon(row.getBigDecimal("lon")).
				mesoRegion(row.getString("mesoregion")).
				microRegion(row.getString("microregion")).
				noAccents(row.getString("no_accents")).
				build();
	}

}