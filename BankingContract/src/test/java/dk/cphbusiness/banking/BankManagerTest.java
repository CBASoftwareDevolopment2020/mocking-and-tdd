package dk.cphbusiness.banking;

import static dk.cphbusiness.banking.BankManagerHolder.*;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.junit.Assume.*;

public class BankManagerTest {

    @Test
    public void testAddAccount() {
        assumeThat(manager, not(nullValue()));

        String accountNumber = "12345678";

        AccountDTO acc = manager.addAccount(accountNumber, "id123", "steph123");

        assertThat(acc, not(nullValue()));
        assertEquals(accountNumber, acc.getNumber());
    }

    @Test
    public void testAddCustomer() {
        assumeThat(manager, not(nullValue()));

        String cpr = "cp123";
        String name = "bob";

        CustomerDTO customer = manager.addCustomer(cpr, name, "account", "bank");

        assertThat(customer, not(nullValue()));
        assertEquals(cpr, customer.getCpr());
        assertEquals(name, customer.getName());
    }

    @Test
    public void testGetExistingAccount() {
        assumeThat(manager, not(nullValue()));

        String accountNumber = "12345678";
        AccountDTO actual = manager.getAccount(accountNumber);

        assertThat(actual, not(nullValue()));
        assertEquals(accountNumber, actual.getNumber());
    }

    @Test
    public void testGetCustomerAccounts() {
        assumeThat(manager, not(nullValue()));

        String customerId = "id321";
        List<AccountDTO> accounts = manager.getCustomersAccounts(customerId);

        assertThat(accounts, not(nullValue()));
    }

    @Test
    public void testGetAccounts() {
        assertThat(manager, not(nullValue()));

        List<AccountDTO> accounts = manager.getAccounts();

        assertThat(accounts, not(nullValue()));
    }

    @Test
    public void testGetCustomer() {
        assertThat(manager, not(nullValue()));

        String customerId = "id123";
        CustomerDTO customer = manager.getCustomer(customerId);

        assertThat(customer, not(nullValue()));
    }

    @Test
    public void testGetCustomers() {
        assertThat(manager, not(nullValue()));

        String bankId = "bid123";
        List<CustomerDTO> customer = manager.getCustomers(bankId);

        assertThat(customer, not(nullValue()));
    }

    @Test
    public void testTranfer() {
        assertThat(manager, not(nullValue()));

        String source = "src123";
        String target = "trgt132";
        long amount = 100;

        MovementDTO mov = manager.transfer(source, target, amount);

        assertThat(mov, not(nullValue()));
        assertEquals(source, mov.getSourceAccount());
        assertEquals(target, mov.getTargetAccount());
        assertEquals(amount, mov.getAmount());
    }
}
