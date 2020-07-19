package com.hoqi.practic20.repositories;


import com.hoqi.practic20.models.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ShopCartRepository extends JpaRepository<ShopCart, Integer> {
    Iterable<ShopCart> findByClientId(Integer id);
    ShopCart findByClientIdAndStatus(Integer clientId,Integer status);
}
