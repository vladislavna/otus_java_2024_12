package homework;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> customerServices;
    public CustomerService() {
        customerServices = new TreeMap<>(Comparator.comparingLong(o -> o.getScores()));
    }

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> smallestCustomerService = customerServices.firstEntry();
        return new AbstractMap.SimpleEntry<Customer, String>(
                new Customer(smallestCustomerService.getKey()),
                smallestCustomerService.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Customer customerNext = customerServices.higherKey(customer);
        return customerNext == null
            ? null
            : new AbstractMap.SimpleEntry<Customer, String>(
                new Customer(customerNext),
                customerServices.get(customerNext));
    }

    public void add(Customer customer, String data) {
        customerServices.put(customer, data);
    }
}
