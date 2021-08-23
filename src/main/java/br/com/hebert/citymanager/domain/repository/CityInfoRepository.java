package br.com.hebert.citymanager.domain.repository;

import br.com.hebert.citymanager.domain.dto.*;
import br.com.hebert.citymanager.domain.repository.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class CityInfoRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityInfoRepository.class);
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	 
	public CityInfoRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate; 
	}
	 
	 private final String SQL_STATE_MAX_MIN_CITIES =
			 "SELECT uf, MAX(quant) AS quant " +
					 " FROM  " +
					 "    ( " +
					 "    SELECT uf, COUNT(uf) AS  quant " +
					 "      FROM city  " +
					 "    WHERE uf not in (:excludedStates) " +
					 "    GROUP BY uf  " +
					 "    ORDER BY quant DESC  " +
					 "    LIMIT 1 " +
					 "    )  " +
					 "AS reg_min " +
					 "UNION " +
					 "SELECT uf, MIN( quant) AS  quant " +
					 " FROM  " +
					 "    ( " +
					 "    SELECT uf, COUNT(uf) AS  quant " +
					 "      FROM city  " +
					 "    WHERE uf not in (:excludedStates) " +
					 "    GROUP BY uf  " +
					 "    ORDER BY quant ASC  " +
					 "   LIMIT 1 " +
					 "    ) " +
					 "AS reg_max";

	private final String SQL_CITY_QUANTITY_BY_STATE =
			"SELECT uf, COUNT(uf) as quant " +
			" FROM city " +
			"WHERE uf not in (:excludedStates) " +
			"GROUP BY uf ORDER BY uf ";

	private final String SQL_CITY_NAME_BY_STATE =
			"SELECT  uf, name as city " +
			"      FROM city " +
			"WHERE uf in (:filterStates)" +
			"ORDER BY name ASC";

	private final String SQL_SEARCH_IN_ALL_COLUMNS =
			"SELECT * " +
			" FROM city " +
			"WHERE CONCAT(name,uf,alternative_names,capital,ibge_id,lat,lon,mesoregion,microregion,no_accents) " +
				   "LIKE :searchValue ";

	private final String SQL_COUNT_REGISTRIES_COLUMNS =
			"SELECT COUNT(DISTINCT %s) AS column_count " +
			"  FROM city";

	private final String SQL_BIGGEST_DIST_BETWEEN_CITIES =
			" SELECT a.name AS from_city, a.uf AS from_uf, b.name AS to_city,  b.uf AS to_uf, " +
			"   111.111 * " +
			"    DEGREES(ACOS(LEAST(COS(RADIANS(a.lat)) " +
			"         * COS(RADIANS(b.lat)) " +
			"         * COS(RADIANS(a.lon - b.lon)) " +
			"         + SIN(RADIANS(a.lat)) " +
			"         * SIN(RADIANS(b.lat)), 1.0))) AS distance_in_km " +
			"  FROM city AS a " +
			"  JOIN city AS b ON a.id <> b.id " +
			"    ORDER BY distance_in_km DESC " +
			"  LIMIT 1 ";

	 public List<CityStateQuantityDTO> findStateSmallestAndBiggestByCities(List<String> excludedStates)  {
	        RowMapper<CityStateQuantityDTO> rowMapper = new CityStateQuantityDTOMapper();
	        Map<String, Object> paramMap = new HashMap<String, Object>();
			 if (Objects.nonNull(excludedStates) && !excludedStates.isEmpty()) {
				 paramMap.put("excludedStates", excludedStates);
			 }

	        LOGGER.info("[findStateSmallestAndBiggestByCities] Generating the query");
		 	List<CityStateQuantityDTO> list = namedParameterJdbcTemplate.query(SQL_STATE_MAX_MIN_CITIES, paramMap, rowMapper);
	        LOGGER.info("[findStateSmallestAndBiggestByCities] Finished");

	        return list;
	 }

	public List<CityStateQuantityDTO> findCityQuantityByState(List<String> excludedStates)  {
		RowMapper<CityStateQuantityDTO> rowMapper = new CityStateQuantityDTOMapper();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (Objects.nonNull(excludedStates) && !excludedStates.isEmpty()) {
			paramMap.put("excludedStates", excludedStates);
		}

		LOGGER.info("[findCityQuantityByState] Generating the query");
		List<CityStateQuantityDTO> list = namedParameterJdbcTemplate.query(SQL_CITY_QUANTITY_BY_STATE, paramMap, rowMapper);
		LOGGER.info("[findCityQuantityByState] Finished");

		return list;
	}

	public List<CityStateNameDTO> findCityNameByState(List<String> filterStates)  {
		RowMapper<CityStateNameDTO> rowMapper = new CityStateNameDTOMapper();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (Objects.nonNull(filterStates) && !filterStates.isEmpty()) {
			paramMap.put("filterStates", filterStates);
		}

		LOGGER.info("[findCityNameByState] Generating the query");
		List<CityStateNameDTO> list = namedParameterJdbcTemplate.query(SQL_CITY_NAME_BY_STATE, paramMap, rowMapper);
		LOGGER.info("[findCityNameByState] Finished");

		return list;
	}

	public List<CityDTO> searchValueInAllColumns(String searchValue)  {
		RowMapper<CityDTO> rowMapper = new CityDTOMapper();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("searchValue", "%"+searchValue+"%");

		LOGGER.info("[searchValueInAllColumns] Generating the query");
		List<CityDTO> list = namedParameterJdbcTemplate.query(SQL_SEARCH_IN_ALL_COLUMNS, paramMap, rowMapper);
		LOGGER.info("[searchValueInAllColumns] Finished");

		return list;
	}

	public List<CityDistanceDTO> findBiggestDistance()  {
		RowMapper<CityDistanceDTO> rowMapper = new CityDistanceDTOMapper();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		LOGGER.info("[findBiggestDistance] Generating the query");
		List<CityDistanceDTO> list = namedParameterJdbcTemplate.query(SQL_BIGGEST_DIST_BETWEEN_CITIES, paramMap, rowMapper);
		LOGGER.info("[findBiggestDistance] Finished");

		return list;
	}

	public ColumnCountDTO countRegistersByColumnName(String columnName)  {
		ColumnCountDTO dto = ColumnCountDTO.builder().column(columnName).build();
		Map<String, Object> paramMap = new HashMap<>();
		String sql = String.format(SQL_COUNT_REGISTRIES_COLUMNS,columnName);

		LOGGER.info("[countRegistersByColumnName] Generating the query");
		Long columnCount = namedParameterJdbcTemplate.queryForObject(sql, paramMap, Long.class);
		LOGGER.info("[countRegistersByColumnName] Finished. ColumnCountDTO = {}", dto);

		if (Objects.nonNull(columnCount) ) {
			dto.setNumberOfRegistries(columnCount);
		}

		return dto;
	}

}
