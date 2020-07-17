package com.hoqi.practic20.repositories;

import com.hoqi.practic20.domain.Product;
import com.hoqi.practic20.domain.ShopCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ShopCartItemRepository extends JpaRepository<ShopCartItem, Integer> {
}
