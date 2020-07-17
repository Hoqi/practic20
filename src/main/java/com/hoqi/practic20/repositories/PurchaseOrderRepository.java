package com.hoqi.practic20.repositories;

import com.hoqi.practic20.domain.Product;
import com.hoqi.practic20.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    @Query("select p from PurchaseOrder p where p.shopCart.client.id = ?1")
    public PurchaseOrder findByClientId(Integer id);
}
