package com.demo.controller;

import com.demo.model.Order;
import com.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("")
    private ResponseEntity<Order> createOrder(@RequestBody @Valid Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    private ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestParam Integer quantity) {
        Order createdOrder = orderService.modifyOrder(id, quantity);
        return new ResponseEntity<>(createdOrder, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>("Order has been Successfully deleted !!!!", HttpStatus.OK);
    }

    @GetMapping("")
    private ResponseEntity getOrderBook()
    {
        return new ResponseEntity<>(orderService.getOrderBook(), HttpStatus.OK);
    }

}
