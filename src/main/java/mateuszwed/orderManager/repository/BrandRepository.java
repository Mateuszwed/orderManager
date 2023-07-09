package mateuszwed.orderManager.repository;

import mateuszwed.orderManager.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
