package FinalProject.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Data
public class OrderDto {
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String paymentMethod;
    private double totalPrice;

    private List<CartItem> items = new ArrayList<>();


}
