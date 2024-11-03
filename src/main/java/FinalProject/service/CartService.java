package FinalProject.service;

import FinalProject.model.User;
import FinalProject.repository.UserRepository;
import FinalProject.repository.iPhoneRepository;
import FinalProject.model.CartItem;
import FinalProject.model.iPhone;
import FinalProject.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private iPhoneRepository iPhoneRepository;

    @Autowired
    private UserRepository userRepository;

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void addProductToCart(Long productId) {
        User user = getAuthenticatedUser();
        Optional<iPhone> optionaliPhone = iPhoneRepository.findById(productId);

        if (optionaliPhone.isPresent()) {
            iPhone iphone = optionaliPhone.get();

            Optional<CartItem> existingItem = cartRepository.findByUserAndProductId(user, productId);
            if (existingItem.isPresent()) {
                CartItem cartItem = existingItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartRepository.save(cartItem);
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setUser(user);
                cartItem.setProductId(productId);
                cartItem.setModelName(iphone.getModelName());
                cartItem.setColor(iphone.getColor());
                cartItem.setStorage(iphone.getStorage());
                cartItem.setPrice(iphone.getPrice());
                cartItem.setImage(iphone.getImage());
                cartItem.setQuantity(1);
                cartRepository.save(cartItem);
            }
        } else {
            throw new RuntimeException("Product not found.");
        }
    }

    public void increaseQuantity(Long itemId) {
        User user = getAuthenticatedUser();
        CartItem item = cartRepository.findByIdAndUser(itemId, user)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setQuantity(item.getQuantity() + 1);
        cartRepository.save(item);
    }

    public void decreaseQuantity(Long itemId) {
        User user = getAuthenticatedUser();
        CartItem item = cartRepository.findByIdAndUser(itemId, user)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            cartRepository.save(item);
        } else {
            cartRepository.deleteByIdAndUser(itemId, user);
        }
    }

    public List<CartItem> getAllCartItems() {
        User user = getAuthenticatedUser();
        return cartRepository.findAllByUser(user);
    }

    public void removeCartItem(Long itemId) {
        User user = getAuthenticatedUser();
        cartRepository.deleteByIdAndUser(itemId, user);
    }
}
