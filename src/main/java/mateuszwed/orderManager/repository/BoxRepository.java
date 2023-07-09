package mateuszwed.orderManager.repository;

import mateuszwed.orderManager.model.Box;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxRepository extends JpaRepository<Box, Integer> {
}
