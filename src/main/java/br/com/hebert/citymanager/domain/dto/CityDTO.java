package br.com.hebert.citymanager.domain.dto;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CityDTO extends GenericDTO {
    private static final long serialVersionUID = -4482401478257383173L;

    private Long id;
    private Long ibgeId;
    private String name;
    private String uf;
    private Boolean capital;
    private BigDecimal lon;
    private BigDecimal lat;
    private String noAccents;
    private String alternativeNames;
    private String microRegion;
    private String mesoRegion;
}
