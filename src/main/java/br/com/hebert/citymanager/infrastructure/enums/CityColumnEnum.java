
package br.com.hebert.citymanager.infrastructure.enums;

import lombok.Getter;

@Getter
public enum CityColumnEnum {

	ID("id"),
	IBGE_ID("ibge_id"),
	NAME   ("name"),
	CAPITAL("capital"),
	UF  ("uf"),
	LATITUDE  ("lat"),
	LONGITUDE  ("lon"),
	ALTERNATIVE_NAMES ("alternative_names"),
	MESO_REGION  ("mesoregion"),
	MICRO_REGION  ("microregion"),
	NO_ACCENTS  ("no_accents");

	private String column;

	CityColumnEnum(final String label) {
		this.column = label;
	}

	public static CityColumnEnum getByName(String name) {
		return CityColumnEnum.valueOf(name);
	}
}