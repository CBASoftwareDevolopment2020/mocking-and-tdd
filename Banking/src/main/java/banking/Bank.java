package banking;

import java.util.List;

public interface Bank {
    Account getAccount(String number);

    List<Account> getAccounts();

    List<Account> getAccounts(Customer customer);

    List<Customer> getCustomers();

    String getCvr();

    String getName();

    void registerAccount(Account account);

    Customer getCustomer(String number);

    void registerCustomer(Customer customer);
}