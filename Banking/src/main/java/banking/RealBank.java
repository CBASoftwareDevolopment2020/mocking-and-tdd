package banking;

import java.util.ArrayList;
import java.util.List;

public class RealBank implements Bank {
    private final String name;
    private List<Account> accounts = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private String cvr;

    public RealBank(String cvr, String name) {
        this.cvr = cvr;
        this.name = name;
    }

    @Override
    public Account getAccount(String number) {
        for (Account a : accounts)
            if (a.getNumber().equals(number))
                return a;
        return null;
    }

    @Override
    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    public List<Account> getAccounts(Customer customer) {
        return customer.getAccounts();
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public String getCvr() {
        return cvr;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void registerAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public Customer getCustomer(String number) {
        for (Customer c : customers)
            if (c.getCpr().equals(number))
                return c;
        return null;
    }

    @Override
    public void registerCustomer(Customer customer) {
        customers.add(customer);
    }
}
