package br.com.hebert.citymanager.api.response;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class DataResponse <T> {
    private Integer status;
    private String message;
    private T data;

    public DataResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public DataResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

//    public ResponseEntity<DataResponse<T>> getResponse() {
//       // return ResponseEntity.status(status).body(new DataResponse<T>(status, message, data));
//        DataResponse<T> response = new DataResponse<T>(status, message, data);
//        return new ResponseEntity<>(response, status);
//    }

    //DataResponse<List<CityStateQuantityDTO>> response = new DataResponse<List<CityStateQuantityDTO>>(HttpStatus.OK.value(), StatusResponseEnum.SUCCESS.getLabel(), dtos);
      //return new ResponseEntity<DataResponse<List<CityStateQuantityDTO>>>(response, HttpStatus.OK);

}
