package com.hoqi.practic20.repositories;


import com.hoqi.practic20.models.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ShopCartRepository extends JpaRepository<ShopCart, Integer> {
    @Query("select c from ShopCart c where c.clientId=?1 and c.status=0")
    ShopCart findByClientId(Integer clientId);
}
