package com.hoqi.practic20.services;

import com.hoqi.practic20.exceptions.CartExistException;
import com.hoqi.practic20.exceptions.CartIsEmptyException;
import com.hoqi.practic20.exceptions.NotFoundException;
import com.hoqi.practic20.models.*;
import com.hoqi.practic20.models.requests.SubmitCartRequest;
import com.hoqi.practic20.models.responses.GetOrderResponse;
import com.hoqi.practic20.repositories.ShopCartItemRepository;
import com.hoqi.practic20.repositories.ShopCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

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

    public ShopCart create(Integer userId) throws CartExistException {
        if (shopCartRepository.findByClientId(userId) == null) {
            ShopCart newCart = new ShopCart();
            newCart.setStatus(0);
            newCart.setClientId(userId);
            return shopCartRepository.save(newCart);
        }
        throw new CartExistException("Корзина уже существует");
    }

    public ShopCart get(Integer userId) throws NotFoundException {
        ShopCart shopCart = shopCartRepository.findByClientId(userId);
        if (shopCart == null) throw new NotFoundException("Корзина не найдена");
        shopCart.getShopCartItems().sort(Comparator.comparingInt(ShopCartItem::getId));
        return shopCart;
    }


    public ShopCart changeItemCount(Integer userId, Integer vendorCode,Integer value) throws NotFoundException {
        ShopCart cart = shopCartRepository.findByClientId(userId);
        Product product = productService.get(vendorCode);
        ShopCartItem item = shopCartItemRepository.findByVendorCodeAndCartId(vendorCode, cart.getId());
        if (item != null) {
            int newCount = item.getCount() + value;
            if (newCount > 0){
                item.setCount(newCount);
                shopCartItemRepository.save(item);
            } else {
                shopCartItemRepository.delete(item);
            }
        } else {
            item = new ShopCartItem(product, cart);
            shopCartItemRepository.save(item);
        }
        return shopCartRepository.findByClientId(userId);
    }

    public GetOrderResponse submit(Integer userId,SubmitCartRequest data) throws CartIsEmptyException,NotFoundException {
        ShopCart cart = this.get(userId);
        if (!cart.getShopCartItems().isEmpty()) {
            cart.setStatus(1);
            shopCartRepository.save(cart);
        } else throw new CartIsEmptyException("Нет товаров в корзине");
        return purchaseOrderService.create(data, cart);
    }

}
