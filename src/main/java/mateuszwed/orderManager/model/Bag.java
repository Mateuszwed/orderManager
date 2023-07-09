package mateuszwed.orderManager.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String bagType;
    int quantity;
}
