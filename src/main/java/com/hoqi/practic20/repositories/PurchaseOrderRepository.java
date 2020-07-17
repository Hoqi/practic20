package com.hoqi.practic20.repositories;

import com.hoqi.practic20.models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    @Query("select p from PurchaseOrder p where p.shopCart.clientId= ?1")
    public PurchaseOrder findByClientId(Integer id);
}
