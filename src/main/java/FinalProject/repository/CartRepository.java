package FinalProject.repository;


import FinalProject.model.CartItem;
import FinalProject.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByUserAndProductId(User user, Long productId);
    Optional<CartItem> findByIdAndUser(Long itemId, User user);
    @Modifying
    @Transactional
    void deleteByIdAndUser(Long itemId, User user);
    List<CartItem> findAllByUser(User user);
}