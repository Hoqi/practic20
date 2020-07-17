package com.hoqi.practic20.repositories;


import com.hoqi.practic20.models.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ShopCartRepository extends JpaRepository<ShopCart, Integer> {
    @Query("Select c from ShopCart c where c.id = (select max(c2.id) from ShopCart c2 where c2.clientId = ?1)")
    ShopCart findByClientId(Integer id);

}
