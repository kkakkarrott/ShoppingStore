package walmart.hackathon.shopapi.service.impl;


import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import walmart.hackathon.shopapi.entity.Cart;
import walmart.hackathon.shopapi.entity.OrderMain;
import walmart.hackathon.shopapi.entity.ProductInOrder;
import walmart.hackathon.shopapi.entity.User;
import walmart.hackathon.shopapi.repository.CartRepository;
import walmart.hackathon.shopapi.repository.OrderRepository;
import walmart.hackathon.shopapi.repository.ProductInOrderRepository;
import walmart.hackathon.shopapi.repository.UserRepository;
import walmart.hackathon.shopapi.service.CartService;
import walmart.hackathon.shopapi.service.ProductService;
import walmart.hackathon.shopapi.service.UserService;

/**
 * Created By Zhu Lin on 3/11/2018.
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductInOrderRepository productInOrderRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;

    @Override
    public Cart getCart(User user) {
        return user.getCart();
    }

    @Override
    @Transactional
    public void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user) {
        Cart finalCart = user.getCart();
        productInOrders.forEach(productInOrder -> {
            Set<ProductInOrder> set = finalCart.getProducts();
            Optional<ProductInOrder> old = set.stream().filter(e -> e.getProductId().equals(productInOrder.getProductId())).findFirst();
            ProductInOrder prod;
            if (old.isPresent()) {
                prod = old.get();
                prod.setCount(productInOrder.getCount() + prod.getCount());
            } else {
                prod = productInOrder;
                prod.setCart(finalCart);
                finalCart.getProducts().add(prod);
            }
            productInOrderRepository.save(prod);
        });
        cartRepository.save(finalCart);

    }

    @Override
    @Transactional
    public void delete(String itemId, User user) {
    	Set<ProductInOrder> productLSet = user.getCart().getProducts();
    	Optional<ProductInOrder> op = productLSet.stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
     //   var op = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
    	op.ifPresent(productInOrder -> {
            productInOrder.setCart(null);
            productInOrderRepository.delete(productInOrder.getId());
        });
    }



    @Override
    @Transactional
    public void checkout(User user) {
        // Creat an order
        OrderMain order = new OrderMain(user);
        orderRepository.save(order);

        // clear cart's foreign key & set order's foreign key& decrease stock
        user.getCart().getProducts().forEach(productInOrder -> {
            productInOrder.setCart(null);
            productInOrder.setOrderMain(order);
            productService.decreaseStock(productInOrder.getProductId(), productInOrder.getCount());
            productInOrderRepository.save(productInOrder);
        });

    }
}
