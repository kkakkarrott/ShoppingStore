package walmart.hackathon.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import walmart.hackathon.shopapi.entity.ProductInOrder;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {

}
