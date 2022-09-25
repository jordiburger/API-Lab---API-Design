package de.berlin.htw.control;

import java.util.ArrayList;
import java.util.List;

import de.berlin.htw.boundary.dto.Order;
import de.berlin.htw.boundary.dto.Orders;

public class OrderService {

    public Orders getOrders(final String userId) {
        // find all orders of a single user
        List<Order> orders = findOrders(userId);
        return new Orders(orders);
    }

    private List<Order> findOrders(final String userId) {
        // TODO Auto-generated method stub
        return new ArrayList<>();
    }
}
