package com.demo.service;

import com.demo.exception.OrderNotExists;
import com.demo.model.Order;
import com.demo.model.OrderBook;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderBook orderBook = new OrderBook(new HashMap<>(), new HashMap<>());

    private final Map<String, Order> idMap = new HashMap<>();

    public Order createOrder(Order order) {

        order.setId(UUID.randomUUID().toString());
        idMap.put(order.getId(), order);
        List<Order> orders;
        if (order.getSide().equalsIgnoreCase("buy")) {
            orders = orderBook.getBuyList().get(order.getPrice());
            if (isEmpty(orders)) {
                List<Order> newOrder = new ArrayList<>();
                newOrder.add(order);
                orderBook.getBuyList().put(order.getPrice(), newOrder);
            } else {
                orders.add(order);
            }
        } else {
            orders = orderBook.getSellList().get(order.getPrice());
            if (isEmpty(orders)) {
                List<Order> newOrder = new ArrayList<>();
                newOrder.add(order);
                orderBook.getSellList().put(order.getPrice(), newOrder);
            } else {
                orders.add(order);
            }
        }
        return order;
    }


    public Order modifyOrder(String id, int newQuantity) {
        Order oldOrder = idMap.get(id);
        if (oldOrder != null) {
            List<Order> orders;
            if (oldOrder.getSide().equalsIgnoreCase("buy")) {
                orders = orderBook.getBuyList().get(oldOrder.getPrice());
                orderBook.getBuyList().put(oldOrder.getPrice(), orders.stream().filter(order -> !oldOrder.getId().equalsIgnoreCase(order.getId())).collect(Collectors.toList()));
                oldOrder.setQuantity(newQuantity);
                orderBook.getBuyList().get(oldOrder.getPrice()).add(oldOrder);
            } else {
                orders = orderBook.getSellList().get(oldOrder.getPrice());
                orderBook.getSellList().put(oldOrder.getPrice(), orders.stream().filter(order -> !oldOrder.getId().equalsIgnoreCase(order.getId())).collect(Collectors.toList()));
                oldOrder.setQuantity(newQuantity);
                orderBook.getSellList().get(oldOrder.getPrice()).add(oldOrder);
            }
            return oldOrder;
        } else {
            throw new OrderNotExists("Order id not exists");
        }
    }


    public boolean deleteOrder(String id) {
        Order oldOrder = idMap.get(id);
        if (oldOrder != null) {
            List<Order> orders;
            if (oldOrder.getSide().equalsIgnoreCase("buy")) {
                orders = orderBook.getBuyList().get(oldOrder.getPrice());
                orderBook.getBuyList().put(oldOrder.getPrice(), orders.stream().filter(order -> !oldOrder.getId().equalsIgnoreCase(order.getId())).collect(Collectors.toList()));
            } else {
                orders = orderBook.getSellList().get(oldOrder.getPrice());
                orderBook.getSellList().put(oldOrder.getPrice(), orders.stream().filter(order -> !oldOrder.getId().equalsIgnoreCase(order.getId())).collect(Collectors.toList()));
            }
            idMap.remove(id);
        } else {
            throw new OrderNotExists("Order id not exists");
        }
        return true;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }
}
