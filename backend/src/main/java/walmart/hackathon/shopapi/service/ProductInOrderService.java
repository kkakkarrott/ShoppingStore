package walmart.hackathon.shopapi.service;

import walmart.hackathon.shopapi.entity.ProductInOrder;
import walmart.hackathon.shopapi.entity.User;

public interface ProductInOrderService {
    void update(String itemId, Integer quantity, User user);
    ProductInOrder findOne(String itemId, User user);
}
