package FinalProject.controller;

import FinalProject.model.Order;
import FinalProject.model.OrderDto;
import FinalProject.service.CartService;
import FinalProject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;


    @PostMapping("/submit")
    public String submitOrder(@ModelAttribute("orderDto")  OrderDto orderDto, Model model) {
        if (orderDto.getItems().isEmpty()) {
            orderDto.setItems(cartService.getAllCartItems());
        }
        Order order = orderService.createOrder(orderDto);
        model.addAttribute("order", order);
        return "order-confirmation";
    }
}



