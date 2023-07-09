package mateuszwed.orderManager.repository;

import mateuszwed.orderManager.model.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Integer> {
    @Query("SELECT s FROM ShippingMethod s LEFT JOIN FETCH s.products p LEFT JOIN FETCH p.productPackaging")
    List<ShippingMethod> findAll();
}
