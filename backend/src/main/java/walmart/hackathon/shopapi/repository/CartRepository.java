package walmart.hackathon.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import walmart.hackathon.shopapi.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Integer> {
}
