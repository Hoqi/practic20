package com.hoqi.practic20.repositories;

import com.hoqi.practic20.domain.Product;
import com.hoqi.practic20.domain.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ShopCartRepository extends JpaRepository<ShopCart, Integer> {
    @Query("Select c from ShopCart c Where c.client.id = ?1")
    ShopCart findByClientId(Integer id);

    @Modifying
    @Query(value = "insert into ShopCart(client_id) values (:client_id)",
        nativeQuery = true)
    void saveWithUserId(@Param("client_id") Integer client_id);

}
