package banking;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BankTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void testGetAccount() {
        Bank bank = new RealBank("a","BoB");
        Customer customer = context.mock(Customer.class);
        Account expectedAccount = new RealAccount(bank, customer, "1234");

        bank.registerAccount(expectedAccount);

        Account actualAccount = bank.getAccount("1234");

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void testGetAccountDoesNotExists() {
        Bank bank = new RealBank("a","BoB");

        Account actualAccount = bank.getAccount("1234");

        assertNull(actualAccount);
    }

    @Test
    public void testGetCustomer() {
        Bank bank = new RealBank("a","BoB");
        Customer expectedCustomer = new RealCustomer(bank,"abcd", "stephan");

        bank.registerCustomer(expectedCustomer);

        Customer actualCustomer = bank.getCustomer("abcd");

        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    public void testGetCustomerDoesNotExists() {
        Bank bank = new RealBank("a","BoB");

        Customer actualCustomer = bank.getCustomer("abcd");

        assertNull(actualCustomer);
    }

    @Test
    public void testGetAccounts() {
        Bank bank = new RealBank("a","BoB");
        Customer customer = context.mock(Customer.class);
        List<Account> accounts = new ArrayList<>();
        Account actualAccount = new RealAccount(bank, customer, "1234");
        accounts.add(actualAccount);

        context.checking(new Expectations() {{
            allowing(customer).getAccounts();
            will(returnValue(accounts));
        }});

        List<Account> actualAccounts = bank.getAccounts(customer);

        assertEquals(accounts, actualAccounts);
    }

    @Test(expected = NullPointerException.class)
    public void testGetAccountsCustomerDoesNotExists() {
        Bank bank = new RealBank("a","BoB");
        Customer customer = null;
        List<Account> accounts = new ArrayList<>();
        Account actualAccount = new RealAccount(bank, customer, "1234");
        accounts.add(actualAccount);

        List<Account> actualAccounts = bank.getAccounts(customer);
    }

    @Test
    public void testGetters() {
        String cvr = "abcd";
        String name = "stephan";
        Bank bank = new RealBank(cvr, name);
        List<Customer> customers = new ArrayList<>();
        Customer customer = context.mock(Customer.class);
        customers.add(customer);
        bank.registerCustomer(customer);

        List<Account> accounts = new ArrayList<>();
        Account account = context.mock(Account.class);
        accounts.add(account);
        bank.registerAccount(account);

        assertEquals(cvr, bank.getCvr());
        assertEquals(name, bank.getName());
        assertEquals(customers, bank.getCustomers());
        assertEquals(accounts, bank.getAccounts());
    }
}
