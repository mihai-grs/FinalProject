package FinalProject.service;

import FinalProject.exception.OrderNotFoundException;
import FinalProject.model.Order;
import FinalProject.model.iPhone;
import FinalProject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        order.setOrderDate(orderDetails.getOrderDate());
        order.setIphones(orderDetails.getIphones());

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public double calculateTotalPrice(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order can't be found by id: " + orderId));

        return order.getIphones().stream()
                .mapToDouble(iPhone::getPrice)
                .sum();
    }


    public double calculateDiscountedPrice(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        List<iPhone> iphones = order.getIphones();
        double totalPrice = iphones.stream()
                .mapToDouble(iPhone::getPrice)
                .sum();

        List<iPhone> sortedIphones = iphones.stream()
                .sorted(Comparator.comparingDouble(iPhone::getPrice))
                .toList();

        for (int i = 2; i < sortedIphones.size(); i += 3) {
            double discountPrice = sortedIphones.get(i).getPrice() * 0.5;
            totalPrice -= discountPrice;
        }

        return totalPrice;
    }

}

