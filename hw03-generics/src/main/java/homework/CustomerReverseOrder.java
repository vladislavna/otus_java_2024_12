package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private final Deque<Customer> сustomerReverseOrder;

    public CustomerReverseOrder() {
        сustomerReverseOrder = new ArrayDeque<>();
    }

    public void add(Customer customer) {
        сustomerReverseOrder.push(customer);
    }

    public Customer take() {
        return сustomerReverseOrder.pop();
    }
}
