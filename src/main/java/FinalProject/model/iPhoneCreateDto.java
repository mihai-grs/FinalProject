package FinalProject.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Setter
@Getter
public class iPhoneCreateDto {

    private String modelName;
    private String color;
    private Integer storage;


}
