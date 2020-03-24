package banking;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class AccountTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void testGetBalancePositive() {
        final Bank bank = context.mock(Bank.class);
        final Customer customer = context.mock(Customer.class);
        final Movement deposit = context.mock(Movement.class, "deposit");
        final Movement withdrawal = context.mock(Movement.class, "withdrawal");

        Account account = new RealAccount(bank, customer, "1234");

        context.checking(new Expectations() {{
            oneOf(deposit).getAmount();
            will(returnValue(100_00L));
        }});

        context.checking(new Expectations() {{
            oneOf(withdrawal).getAmount();
            will(returnValue(50_00L));
        }});

        account.addDeposit(deposit);
        account.addWithdrawal(withdrawal);
        long res = account.getBalance();

        assertEquals(50_00, res);
    }

    @Test
    public void testGetBalanceZero() {
        final Bank bank = context.mock(Bank.class);
        final Customer customer = context.mock(Customer.class);
        final Movement deposit = context.mock(Movement.class, "deposit");
        final Movement withdrawal = context.mock(Movement.class, "withdrawal");

        Account account = new RealAccount(bank, customer, "1234");

        context.checking(new Expectations() {{
            oneOf(deposit).getAmount();
            will(returnValue(50_00L));
        }});

        context.checking(new Expectations() {{
            oneOf(withdrawal).getAmount();
            will(returnValue(50_00L));
        }});

        account.addDeposit(deposit);
        account.addWithdrawal(withdrawal);
        long res = account.getBalance();

        assertEquals(0_00, res);
    }

    @Test
    public void testGetBalanceNegative() {
        final Bank bank = context.mock(Bank.class);
        final Customer customer = context.mock(Customer.class);
        final Movement deposit = context.mock(Movement.class, "deposit");
        final Movement withdrawal = context.mock(Movement.class, "withdrawal");

        Account account = new RealAccount(bank, customer, "1234");

        context.checking(new Expectations() {{
            oneOf(deposit).getAmount();
            will(returnValue(50_00L));
        }});

        context.checking(new Expectations() {{
            oneOf(withdrawal).getAmount();
            will(returnValue(100_00L));
        }});

        account.addDeposit(deposit);
        account.addWithdrawal(withdrawal);
        long res = account.getBalance();

        assertEquals(-50_00, res);
    }

    @Test
    public void testTransferAccount() {
        final Bank bank = context.mock(Bank.class);
        final Customer customer = context.mock(Customer.class);

        Account sourceAccount = new RealAccount(bank, customer, "1234");
        Account targetAccount = new RealAccount(bank, customer, "5678");

        sourceAccount.transfer(100_00L, targetAccount);

        assertEquals(-100_00, sourceAccount.getBalance());
        assertEquals(100_00, targetAccount.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferAccountNegativeAmount() {
        final Bank bank = context.mock(Bank.class);
        final Customer customer = context.mock(Customer.class);

        Account sourceAccount = new RealAccount(bank, customer, "1234");
        Account targetAccount = new RealAccount(bank, customer, "5678");

        sourceAccount.transfer(-100_00L, targetAccount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferAccountDoesNotExists() {
        final Bank bank = context.mock(Bank.class);
        final Customer customer = context.mock(Customer.class);

        Account sourceAccount = new RealAccount(bank, customer, "1234");
        Account targetAccount = null;

        sourceAccount.transfer(-100_00L, targetAccount);
    }

    @Test
    public void testTransferCustomer() {
        final Bank bank = context.mock(Bank.class);

        final Customer customer = context.mock(Customer.class, "customer");
        Account sourceAccount = new RealAccount(bank, customer, "1234");

        String targetCustomerNumber = "abcd";
        final Customer targetCustomer = context.mock(Customer.class, "targetCustomer");
        Account targetAccount = new RealAccount(bank, targetCustomer, "5678");

        context.checking(new Expectations() {{
            allowing(bank).getCustomer(targetCustomerNumber);
            will(returnValue(targetCustomer));
        }});

        context.checking(new Expectations() {{
            allowing(targetCustomer).getFirstAccount();
            will(returnValue(targetAccount));
        }});

        sourceAccount.transfer(100_00L, targetCustomerNumber);

        assertEquals(-100_00, sourceAccount.getBalance());
        long actual = bank.getCustomer(targetCustomerNumber).getFirstAccount().getBalance();
        assertEquals(100_00, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferCustomerNegativeAmount() {
        final Bank bank = context.mock(Bank.class);

        final Customer customer = context.mock(Customer.class, "customer");
        Account sourceAccount = new RealAccount(bank, customer, "1234");

        String targetCustomerNumber = "abcd";
        final Customer targetCustomer = context.mock(Customer.class, "targetCustomer");
        Account targetAccount = new RealAccount(bank, targetCustomer, "5678");

        context.checking(new Expectations() {{
            allowing(bank).getCustomer(targetCustomerNumber);
            will(returnValue(targetCustomer));
        }});

        context.checking(new Expectations() {{
            allowing(targetCustomer).getFirstAccount();
            will(returnValue(targetAccount));
        }});

        sourceAccount.transfer(-100_00L, targetCustomerNumber);
    }

    @Test(expected = NullPointerException.class)
    public void testTransferCustomerDoesNotExists() {
        final Bank bank = context.mock(Bank.class);

        final Customer customer = context.mock(Customer.class, "customer");
        Account sourceAccount = new RealAccount(bank, customer, "1234");

        String targetCustomerNumber = "abcd";

        context.checking(new Expectations() {{
            allowing(bank).getCustomer(targetCustomerNumber);
            will(returnValue(null));
        }});

        sourceAccount.transfer(100_00L, targetCustomerNumber);
    }

    @Test
    public void testGetters() {
        Bank bank = context.mock(Bank.class);
        Customer customer = context.mock(Customer.class);
        String number = "1234";

        Account source = new RealAccount(bank,customer,number);

        List<Movement> deposits = new ArrayList<>();
        Movement deposit = context.mock(Movement.class,"deposit");
        deposits.add(deposit);
        List<Movement> withdrawals = new ArrayList<>();
        Movement withdrawal = context.mock(Movement.class,"withdrawal");
        withdrawals.add(withdrawal);

        source.addDeposit(deposit);
        source.addWithdrawal(withdrawal);

        assertEquals(bank, source.getBank());
        assertEquals(customer, source.getCustomer());
        assertEquals(number, source.getNumber());
        assertEquals(deposits, source.getDeposits());
        assertEquals(withdrawals, source.getWithdrawals());
    }
}
