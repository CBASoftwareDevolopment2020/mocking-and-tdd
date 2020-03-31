package banking;

import banking.data.access.DAO;
import banking.data.access.DBConnector;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

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

}
