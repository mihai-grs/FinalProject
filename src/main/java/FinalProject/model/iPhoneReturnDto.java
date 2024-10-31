package FinalProject.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Data
public class iPhoneReturnDto {
    private Long id;
    private String modelName;
    private String color;
    private Integer storage;
    private LocalDate releaseDate;
    private Double price;
    private Integer stock;
    private String image;

}
