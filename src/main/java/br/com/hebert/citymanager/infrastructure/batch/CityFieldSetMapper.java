package br.com.hebert.citymanager.infrastructure.batch;

import br.com.hebert.citymanager.domain.model.City;
import br.com.hebert.citymanager.infrastructure.enums.CityColumnEnum;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CityFieldSetMapper implements FieldSetMapper<City> {

    @Override
    public City mapFieldSet(FieldSet fieldSet) throws BindException {
        // Creates a new instance of City
        return  City.builder().
                ibgeId(fieldSet.readLong(CityColumnEnum.IBGE_ID.getColumn())).
                name(fieldSet.readString(CityColumnEnum.NAME.getColumn())).
                capital(fieldSet.readBoolean(CityColumnEnum.CAPITAL.getColumn())).
                uf(fieldSet.readString(CityColumnEnum.UF.getColumn())).
                lat(fieldSet.readBigDecimal(CityColumnEnum.LATITUDE.getColumn())).
                lon(fieldSet.readBigDecimal(CityColumnEnum.LONGITUDE.getColumn())).
                alternativeNames(fieldSet.readString(CityColumnEnum.ALTERNATIVE_NAMES.getColumn())).
                mesoRegion(fieldSet.readString(CityColumnEnum.MESO_REGION.getColumn())).
                microRegion(fieldSet.readString(CityColumnEnum.MICRO_REGION.getColumn())).
                noAccents(fieldSet.readString(CityColumnEnum.NO_ACCENTS.getColumn())).
                build();
    }
}
