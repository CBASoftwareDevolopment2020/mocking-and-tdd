package banking;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void testTransfer() {
        final Bank bank = context.mock(Bank.class);
        final Customer targetCustomer = context.mock(Customer.class);

        Customer sourceCustomer = new RealCustomer(bank, "abcd", "stephan");
        Account sourceAccount = new RealAccount(bank, sourceCustomer, "1234");
        Account targetAccount = new RealAccount(bank, targetCustomer, "5678");

        context.checking(new Expectations() {{
            oneOf(targetCustomer).getFirstAccount();
            will(returnValue(targetAccount));
        }});


        sourceCustomer.transfer(100_00L, sourceAccount, targetCustomer);

        assertEquals(100_00, targetAccount.getBalance());
        assertEquals(-100_00, sourceAccount.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferNegativeAmount() {
        final Bank bank = context.mock(Bank.class);
        final Customer targetCustomer = context.mock(Customer.class);

        Customer sourceCustomer = new RealCustomer(bank, "abcd", "stephan");
        Account sourceAccount = new RealAccount(bank, sourceCustomer, "1234");
        Account targetAccount = new RealAccount(bank, targetCustomer, "5678");

        context.checking(new Expectations() {{
            oneOf(targetCustomer).getFirstAccount();
            will(returnValue(targetAccount));
        }});


        sourceCustomer.transfer(-100_00L, sourceAccount, targetCustomer);
    }

    @Test(expected = NullPointerException.class)
    public void testTransferCustomerDoesNotExists() {
        final Bank bank = context.mock(Bank.class);
        final Customer targetCustomer = context.mock(Customer.class);

        Customer sourceCustomer = new RealCustomer(bank, "abcd", "stephan");
        Account sourceAccount = new RealAccount(bank, sourceCustomer, "1234");

        context.checking(new Expectations() {{
            oneOf(targetCustomer).getFirstAccount();
            will(returnValue(null));
        }});


        sourceCustomer.transfer(100_00L, sourceAccount, targetCustomer);
    }

    @Test(expected = NullPointerException.class)
    public void testTransferAccountDoesNotExists() {
        final Bank bank = context.mock(Bank.class);
        final Customer targetCustomer = context.mock(Customer.class);

        Customer sourceCustomer = new RealCustomer(bank, "abcd", "stephan");
        Account sourceAccount = null;
        Account targetAccount = new RealAccount(bank, targetCustomer, "5678");

        context.checking(new Expectations() {{
            oneOf(targetCustomer).getFirstAccount();
            will(returnValue(null));
        }});


        sourceCustomer.transfer(100_00L, sourceAccount, targetCustomer);
    }

    @Test
    public void testGetters(){
        Bank bank = context.mock(Bank.class);
        String cpr = "abcd";
        String name = "stephan";

        Customer customer = new RealCustomer(bank, cpr, name);
        List<Account> accounts = new ArrayList<>();
        Account firstAccount = context.mock(Account.class);
        accounts.add(firstAccount);
        customer.addAccount(firstAccount);

        assertEquals(bank, customer.getBank());
        assertEquals(cpr, customer.getCpr());
        assertEquals(name, customer.getName());
        assertEquals(accounts, customer.getAccounts());
        assertEquals(firstAccount, customer.getFirstAccount());
    }
}
