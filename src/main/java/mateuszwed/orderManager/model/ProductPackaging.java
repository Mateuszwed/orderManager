package mateuszwed.orderManager.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "pack_type")
public class ProductPackaging {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_packaging_id")  // Zmienione na to, co jest w Liquibase
    List<Box> boxes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_packaging_id")  // Zakładam, że w skrypcie Liquibase dla Bag jest podobnie zdefiniowane
    List<Bag> bags;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_packaging_id")  // Zakładam, że w skrypcie Liquibase dla Brand jest podobnie zdefiniowane
    List<Brand> brands;
}