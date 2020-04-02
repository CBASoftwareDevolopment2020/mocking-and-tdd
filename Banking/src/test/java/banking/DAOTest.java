package banking;

import banking.data.access.DAO;
import banking.data.access.DBConnector;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DAOTest {
    DAO dao = new DAO(DBConnector.getFakeConnection());

    @BeforeClass
    public static void setupClass(){
        try {
            DBConnector
                    .getFakeConnection()
                    .prepareStatement("call setup_fbanking()")
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    @BeforeEach
    void init() {
        try {
            DBConnector
                    .getFakeConnection()
                    .prepareStatement("call setup_fbanking()")
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    @Test
    public void testGetBalance() {

        List<Movement> deps = dao.getDeposits("src123");
        List<Movement> withs = dao.getWithdrawals("src123");

        assertEquals(2, deps.size());
        assertEquals(1, withs.size());


        long expTotalDeposits = 500L;
        long resTotalDeposits = deps
                .stream()
                .map(Movement::getAmount)
                .reduce(0L, Long::sum);

        assertEquals(expTotalDeposits, resTotalDeposits);


        long expTotalWithdrawals = 300L;
        long resTotalWithdrawals= withs
                .stream()
                .map(Movement::getAmount)
                .reduce(0L, Long::sum);

        assertEquals(expTotalWithdrawals, resTotalWithdrawals);


        assertEquals(200L, resTotalDeposits-resTotalWithdrawals);
    }

    /* ACCOUNT */

    @Test
    public void testGetExistingAccount(){
        Account acc = dao.getAccount("42069");
        assertNotNull(acc);

        String accountOwnerName = acc.getCustomer().getName();
        assertEquals("Jacob", accountOwnerName);
    }

    @Test
    public void testGetNoneExistingAccount(){
        /* Add movements to the dao */
        Account acc = dao.getAccount("999999x");
        assertNull(acc);
    }

    @Test
    public void testAddAccountSuccess(){
        dao.addAccount("962734", "id420");
    }

    @Test
    public void testAddAccountFail(){
        String realCusId = "id420";
        String fakeCusId = "fid420";

        String realAccId = "src123";
        String fakeAccId = "fsrc123";

        /* should fail as an account with given account_id already exist */
        assertEquals(0, dao.addAccount(realAccId, realCusId));

        /* should fail as given customer_id doesn't exist */
        assertEquals(0, dao.addAccount(fakeAccId, fakeCusId));

    }

    @Test
    public void testGetAllAccounts(){
        List<Account> acc = dao.getAllAccounts();
        assertTrue(acc.size() >= 3);
    }

    @Test
    public void testGetAccountsFromExistingCustomer(){
        List<Account> acc = dao.getAccountsFromCustomer("id123");
        assertTrue(acc.size() >= 3);
    }

    @Test
    public void testGetAccountsFromNoneExistingCustomer(){
        List<Account> acc = dao.getAccountsFromCustomer("fid123");
        assertEquals(0, acc.size());
    }

    @Test
    public void testAccountHasPositiveBalance(){
        Account acc = dao.getAccountWithMovements("src123");

        assertTrue( acc.getBalance() > 0);
        assertEquals(200, acc.getBalance());
    }

    @Test
    public void testAccountHasNegativeBalance(){
        Account acc = dao.getAccountWithMovements("42069");

        assertTrue( acc.getBalance() < 0);
        assertEquals(-200, acc.getBalance());
    }

    @Test
    public void testValidTransfer(){

        String sourceId = "dummy1";
        String targetId = "dummy2";

        // *note Make new dummy accounts that dont interfere with other tests
        dao.addAccount(sourceId, "id420");
        dao.addAccount(targetId, "id420");

        long sourceAccOriginalBalance = dao.getAccountWithMovements(sourceId).getBalance();
        long targetAccOriginalBalance = dao.getAccountWithMovements(targetId).getBalance();

        dao.addMovement(1000, sourceId, targetId);

        long sourceAccNewBalance = dao.getAccountWithMovements(sourceId).getBalance();
        long targetAccNewBalance = dao.getAccountWithMovements(targetId).getBalance();

        assertEquals(sourceAccOriginalBalance-1000, sourceAccNewBalance);
        assertEquals(targetAccOriginalBalance+1000, targetAccNewBalance);
    }

    @Test
    public void testInvalidTransferAccountDoesntExist(){
        String realAccId = "src123";
        String fakeAccId = "fake123";

        // Confirm our realAccId exist & fakeAccId doest not.
        assertNotNull(dao.getAccount(realAccId));
        assertNull(dao.getAccount(fakeAccId));

        // Making invalid transactions
        int firstAttempt = dao.addMovement(1000, realAccId, fakeAccId);
        int secondAttempt = dao.addMovement(1000, fakeAccId, realAccId);
        int thirdAttempt = dao.addMovement(1000, fakeAccId, fakeAccId);
        int fourthAttempt = dao.addMovement(1000, realAccId, realAccId);

        // Assert all our invalid transactions did not pass
        assertEquals(0, firstAttempt);
        assertEquals(0, secondAttempt);
        assertEquals(0, thirdAttempt);
        assertEquals(0, fourthAttempt);
    }

    @Test
    public void testInvalidTransferNegativeAmount(){
        // *note yikes drenge -> vi mangler en constraint i vores DB
        int res = dao.addMovement(-10000, "src123", "42069");
        assertEquals(0, res);
    }

    @Test
    public void testInvalidTransferZeroAmount(){
        // *note yikes drenge -> vi mangler en constraint i vores DB
        int res = dao.addMovement(0, "src123", "42069");
        assertEquals(0, res);
    }

    @Test
    public void testGetExistingCustomer(){

        String realCusId = "id123";
        Customer c = dao.getCustomer(realCusId);

        assertNotNull(c);
        assertEquals(realCusId, c.getCpr());
        assertEquals("Jacob", c.getName());
        assertEquals("bid123", c.getBank().getCvr());
    }

    @Test
    public void testGetNoneExistingCustomer(){

        String fakeCusId = "fid123";
        Customer c = dao.getCustomer(fakeCusId);
        assertNull(c);
    }

    @Test
    public void testGetAllCustomers(){
        assertTrue(dao.getAllCustomers().size()>=3);
    }


    @Test
    public void testAddCustomerToExistingBank(){

        String newCpr = "newCpr123";
        String name = "Daniel";
        String realBankId = "bid123";

        int res = dao.addCustomer(newCpr,name,realBankId);
        int exp = 1;
        assertEquals(exp, res);
    }

    @Test
    public void testAddCustomerToNoneExistingBank(){

        String newCpr = "newCpr123";
        String name = "Daniel";
        String fakeBankId = "fbid123";

        int res = dao.addCustomer(newCpr,name,fakeBankId);
        int exp = 0;

        assertEquals(exp, res);
    }

    @Test
    public void testAddCustomerToExistingBankWithTakenCpr(){

        String usedCpr = "id123";
        String name = "Daniel";
        String realBankId = "bid123";

        int res = dao.addCustomer(usedCpr,name,realBankId);
        int exp = 0;
        assertEquals(exp, res);
    }

    @Test
    public void testGetExistingBank(){
        Bank bank = dao.getBank("bid123");

        assertNotNull(bank);
        assertEquals("bid123", bank.getCvr());
        assertEquals("Slybank", bank.getName());
    }

    @Test
    public void testGetNoneExistingBank(){
        Bank bank = dao.getBank("fbid123");
        assertNull(bank);
    }

    @Test
    public void testGetAllBanks(){
        List<Bank> banks = dao.getAllBanks();
        boolean res = banks.size() >= 1;
        assertTrue(res);
    }

    @Test
    public void testAddBankSuccessfully(){
        int res = dao.addBank("newbid123", "New Slybank");
        int exp = 1;
        assertEquals(exp, res);
    }

    @Test
    public void testAddBankWithExistingBankId(){
        int res = dao.addBank("bid123", "New Slybank");
        int exp = 0;
        assertEquals(exp, res);
    }

}
