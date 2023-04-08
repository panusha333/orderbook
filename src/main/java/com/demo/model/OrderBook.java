package com.demo.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBook {
    private Map<Integer, List<Order>> buyList;
    private Map<Integer, List<Order>> sellList;
}
