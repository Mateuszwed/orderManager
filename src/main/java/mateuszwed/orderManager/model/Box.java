package mateuszwed.orderManager.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@DiscriminatorValue("box")
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String boxType;
    int quantity;
}
