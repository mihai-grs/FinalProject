package FinalProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "iphones")
public class iPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelName;
    private Integer storage;
    @Column(name = "color", length = 20)
    private String color;
    @Column(name = "price", nullable = false)
    private Double price;
    private Integer stock;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    private String image;

}
