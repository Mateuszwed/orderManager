package mateuszwed.orderManager.repository;

import mateuszwed.orderManager.model.ProductPackaging;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductPackagingRepository extends JpaRepository<ProductPackaging, Integer> {
}
