package mateuszwed.orderManager.repository;

import mateuszwed.orderManager.model.Bag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BagRepository extends JpaRepository<Bag, Integer> {
}
