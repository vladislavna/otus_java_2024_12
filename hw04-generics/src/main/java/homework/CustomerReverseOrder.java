package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private final Deque<Customer> dequeCustomerReverse;

    public CustomerReverseOrder() {
        dequeCustomerReverse = new ArrayDeque<>();
    }

    public void add(Customer customer) {
        dequeCustomerReverse.push(customer);
    }

    public Customer take() {
        return dequeCustomerReverse.pop();
    }
}
