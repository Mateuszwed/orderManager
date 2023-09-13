package mateuszwed.orderManager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    @ManyToOne(cascade = CascadeType.ALL)
    ProductPackaging productPackaging;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    ShippingMethod shippingMethod;
}
