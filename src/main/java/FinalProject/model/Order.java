package FinalProject.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate orderDate;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String paymentMethod;
    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @ManyToMany
    @JoinTable(name = "order_iphones",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "iphone_id"))
    private List<iPhone> iphones;

}
