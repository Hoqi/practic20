package com.hoqi.practic20.repositories;

import com.hoqi.practic20.models.ShopCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopCartItemRepository extends JpaRepository<ShopCartItem, Integer> {
    @Query("Select i from ShopCartItem i where i.product.vendorCode = ?1 and i.shopCart.id = ?2")
    ShopCartItem findByVendorCodeAndCartId(Integer productId, Integer CartId);
}
