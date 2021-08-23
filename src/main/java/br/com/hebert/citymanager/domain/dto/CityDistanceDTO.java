package br.com.hebert.citymanager.domain.dto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class CityDistanceDTO {
    private String fromUF;
    private String fromCity;
    private String toUF;
    private String toCity;
    private BigDecimal distanceInKm;
}
