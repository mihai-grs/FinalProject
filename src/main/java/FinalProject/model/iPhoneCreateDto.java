package FinalProject.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Data
@Setter
@Getter
public class iPhoneCreateDto {

    private String modelName;
    private String color;
    private Integer storage;
    private LocalDate releaseDate;
    private Double price;
    private Integer stock;
    private String image;


}
