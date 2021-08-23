package br.com.hebert.citymanager.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CityStateQuantityDTO {
    private String state;
    private BigDecimal cityQuantity;
}
