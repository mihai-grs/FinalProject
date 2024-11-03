package FinalProject.service;

import FinalProject.model.CartItem;
import FinalProject.model.Order;
import FinalProject.model.OrderDto;

import FinalProject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(OrderDto orderDto) {
        double totalPrice = calculateTotalPriceWithDiscount(orderDto.getItems());
        Order order = new Order();
        order.setFirstName(orderDto.getFirstName());
        order.setLastName(orderDto.getLastName());
        order.setAddress1(orderDto.getAddress1());
        order.setAddress2(orderDto.getAddress2());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDate.now());
        orderRepository.save(order);
        return order;
    }

    public double calculateTotalPriceWithDiscount(List<CartItem> items) {
        if (items.isEmpty()) {
            return 0.0;
        }
        List<CartItem> sortedItems = items.stream()
                .sorted(Comparator.comparingDouble(CartItem::getPrice))
                .toList();

        double total = 0.0;
        int itemsLeftToDiscount = items.stream()
                                        .mapToInt(CartItem::getQuantity)
                                        .sum();

        int discountableSets = itemsLeftToDiscount / 3;

        int currentIndex = 0;

        while (discountableSets > 0) {
            int itemsInSet = 0;
            double setTotal = 0.0;
            double cheapestPriceInSet = Double.MAX_VALUE;

            while (itemsInSet < 3 && currentIndex < sortedItems.size()) {
                CartItem item = sortedItems.get(currentIndex);

                int remainingQuantity = Math.min(3 - itemsInSet, item.getQuantity());

                setTotal += remainingQuantity * item.getPrice();
                itemsInSet += remainingQuantity;
                itemsLeftToDiscount -= remainingQuantity;

                if (item.getPrice() < cheapestPriceInSet) {
                    cheapestPriceInSet = item.getPrice();
                }

                if (remainingQuantity == item.getQuantity()) {
                    currentIndex++;
                }
            }
            total += setTotal - (cheapestPriceInSet * 0.5);
            discountableSets--;
        }
        for (int i = currentIndex; i < sortedItems.size(); i++) {
            CartItem item = sortedItems.get(i);
            total += item.getPrice() * item.getQuantity();
        }

        return total;
    }
}



