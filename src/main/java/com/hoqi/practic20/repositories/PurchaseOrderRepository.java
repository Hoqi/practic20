package com.hoqi.practic20.repositories;

import com.hoqi.practic20.models.PurchaseOrder;
import com.hoqi.practic20.models.responses.GetOrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    @Query("select p from PurchaseOrder p where p.shopCart.clientId= ?1 and p.shopCart.status = 1")
    List<GetOrderResponse> findAllByClientId(Integer id);
}
