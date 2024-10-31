package FinalProject.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.time.LocalDate;

@Setter
@Getter
@Data
public class iPhoneUpdateDto {
    private String modelName;
    private Integer storage;
    private String color;
    private Double price;
    private Integer stock;
    private LocalDate releaseDate;
    private String image;

}
