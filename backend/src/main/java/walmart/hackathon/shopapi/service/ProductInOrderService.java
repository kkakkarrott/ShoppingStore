package walmart.hackathon.shopapi.service;

import walmart.hackathon.shopapi.entity.ProductInOrder;
import walmart.hackathon.shopapi.entity.User;

/**
 * Created By Zhu Lin on 1/3/2019.
 */
public interface ProductInOrderService {
    void update(String itemId, Integer quantity, User user);
    ProductInOrder findOne(String itemId, User user);
}
