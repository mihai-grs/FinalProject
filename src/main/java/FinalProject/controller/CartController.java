package FinalProject.controller;

import FinalProject.model.CartItem;
import FinalProject.model.OrderDto;
import FinalProject.service.CartService;

import FinalProject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        List<CartItem> cartItems = cartService.getAllCartItems();
        model.addAttribute("cartItems", cartItems);

        double totalPrice = orderService.calculateTotalPriceWithDiscount(cartItems);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("orderDto", new OrderDto());
        return "checkout";
    }

    @GetMapping("/view")
        public String viewCart(Model model) {
        List<CartItem> cartItems = cartService.getAllCartItems();
        int totalItems = cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("cartItems", cartService.getAllCartItems());
        return "cart";
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long productId) {
        cartService.addProductToCart(productId);
        return ResponseEntity.ok("Produs adăugat în coș");
    }


    @GetMapping("/items")
    public List<CartItem> getAllCartItems() {
        return cartService.getAllCartItems();

    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long itemId) {
        cartService.removeCartItem(itemId);
        return ResponseEntity.ok("Produs eliminat din coș");
    }

    @PatchMapping("/increase/{itemId}")
    public ResponseEntity<String> increaseQuantity(@PathVariable Long itemId) {
        cartService.increaseQuantity(itemId);
        return ResponseEntity.ok("Cantitate crescută");
    }

    @PatchMapping("/decrease/{itemId}")
    public ResponseEntity<String> decreaseQuantity(@PathVariable Long itemId) {
        cartService.decreaseQuantity(itemId);
        return ResponseEntity.ok("Cantitate redusă");
    }

}
