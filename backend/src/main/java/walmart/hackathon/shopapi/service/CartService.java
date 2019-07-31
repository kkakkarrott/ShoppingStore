package walmart.hackathon.shopapi.service;

import java.util.Collection;

import walmart.hackathon.shopapi.entity.Cart;
import walmart.hackathon.shopapi.entity.ProductInOrder;
import walmart.hackathon.shopapi.entity.User;

/**
 * Created By Zhu Lin on 3/10/2018.
 */
public interface CartService {
    Cart getCart(User user);

    void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user);

    void delete(String itemId, User user);

    void checkout(User user);
}
