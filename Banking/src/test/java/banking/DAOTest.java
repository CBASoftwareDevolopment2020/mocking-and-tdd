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

        // *note burde nok ikke gå igennem
        // int fourthAttempt = dao.addMovement(1000, realAccId, realAccId);

        // Assert all our invalid transactions did not pass
        assertEquals(0, firstAttempt);
        assertEquals(0, secondAttempt);
        assertEquals(0, thirdAttempt);
        // *note
        //assertEquals(0, fourthAttempt);
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

}
