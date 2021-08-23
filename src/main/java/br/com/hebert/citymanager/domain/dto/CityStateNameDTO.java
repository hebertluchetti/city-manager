package br.com.hebert.citymanager.domain.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CityStateNameDTO {
    private String state;
    private String cityName;
}
