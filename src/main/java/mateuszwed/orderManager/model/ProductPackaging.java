package mateuszwed.orderManager.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductPackaging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "box_product_packaging")
    List<Box> boxes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bag_product_packaging")
    List<Bag> bags;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_product_packaging")
    List<Brand> brands;
}
