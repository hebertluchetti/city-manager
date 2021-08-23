package br.com.hebert.citymanager.domain.dto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class ColumnCountDTO {
    private String column;
    private Long numberOfRegistries;
}
