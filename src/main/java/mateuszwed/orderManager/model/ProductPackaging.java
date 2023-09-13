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
    @JoinColumn(name = "box_product_packaging")
    List<Box> boxes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bag_product_packaging")
    List<Bag> bags;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_product_packaging")
    List<Brand> brands;
}
