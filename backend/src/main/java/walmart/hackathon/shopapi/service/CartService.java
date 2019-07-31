package walmart.hackathon.shopapi.service;

import walmart.hackathon.shopapi.entity.Cart;
import walmart.hackathon.shopapi.entity.ProductInOrder;
import walmart.hackathon.shopapi.entity.User;

import java.util.Collection;

public interface CartService {
    Cart getCart(User user);

    void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user);

    void delete(String itemId, User user);

    void checkout(User user);
}
