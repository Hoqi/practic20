package com.hoqi.practic20.services;

import com.hoqi.practic20.models.*;
import com.hoqi.practic20.repositories.ShopCartItemRepository;
import com.hoqi.practic20.repositories.ShopCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopCartService {
    private final ShopCartRepository shopCartRepository;
    private final ShopCartItemRepository shopCartItemRepository;


    private final ProductService productService;
    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public ShopCartService(ShopCartRepository shopCartRepository,
                           ShopCartItemRepository shopCartItemRepository,
                           ProductService productService,
                           PurchaseOrderService purchaseOrderService) {
        this.shopCartRepository = shopCartRepository;
        this.shopCartItemRepository = shopCartItemRepository;
        this.productService = productService;
        this.purchaseOrderService = purchaseOrderService;
    }

    public boolean create(Integer userId) {
        if (shopCartRepository.findByClientIdAndStatus(userId, 0) == null) {
            ShopCart newCart = new ShopCart(userId);
            newCart.setStatus(0);
            shopCartRepository.save(newCart);
            return true;
        }
        return false;
    }

    public ShopCart get(Integer userId) {
        return shopCartRepository.findByClientIdAndStatus(userId, 0);
    }

    public boolean addItem(Integer userId, Integer vendorCode) {
        ShopCart cart = shopCartRepository.findByClientIdAndStatus(userId, 0);
        Product product = productService.get(vendorCode);
        if (cart != null && product != null) {
            ShopCartItem item = shopCartItemRepository.findByVendorCodeAndCartId(vendorCode, cart.getId());
            if (item != null) {
                item.setCount(item.getCount() + 1);
            } else {
                item = new ShopCartItem(product, cart);
            }
            shopCartItemRepository.save(item);
            return true;
        }
        return false;
    }

    public boolean deleteOne(Integer userId, Integer vendorCode) {
        ShopCart cart = shopCartRepository.findByClientIdAndStatus(userId, 0);
        Product product = productService.get(vendorCode);
        if (cart != null && product != null) {
            ShopCartItem item = shopCartItemRepository.findByVendorCodeAndCartId(vendorCode, cart.getId());
            if (item != null) {
                item.setCount(item.getCount() - 1);
                if (item.getCount() == 0) deleteFull(userId, vendorCode);
                else shopCartItemRepository.save(item);
                return true;
            }
        }
        return false;
    }

    public boolean deleteFull(Integer userId, Integer vendorCode) {
        ShopCart cart = shopCartRepository.findByClientIdAndStatus(userId, 0);
        Product product = productService.get(vendorCode);
        if (cart != null && product != null) {
            ShopCartItem item = shopCartItemRepository.findByVendorCodeAndCartId(vendorCode, cart.getId());
            if (item != null) {
                shopCartItemRepository.delete(item);
                return true;
            }
        }
        return false;
    }

    public boolean submit(Integer userId,
                          String productionMethod,
                          String paymentMethod,
                          String address) {
        ShopCart cart = shopCartRepository.findByClientIdAndStatus(userId, 0);
        if (cart != null && !cart.getShopCartItems().isEmpty()) {
            cart.setStatus(1);
            shopCartRepository.save(cart);
        } else return false;
        return purchaseOrderService.create(productionMethod, paymentMethod, address, cart);
    }

}
