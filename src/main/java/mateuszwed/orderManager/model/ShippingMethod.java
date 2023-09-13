package mateuszwed.orderManager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
/*    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "shipping_method")
    List<Product> products;*/
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "shipping_method")
    @JsonManagedReference
    List<Product> products;


    public void addProduct(Product product){
        products.add(product);
    }
}
