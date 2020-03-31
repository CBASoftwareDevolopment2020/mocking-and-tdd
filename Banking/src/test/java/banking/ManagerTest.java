package banking;

import banking.data.access.DAO;
import banking.data.access.DBConnector;
import banking.data.access.FakeDB;
import dk.cphbusiness.banking.BankManagerHolder;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import dk.cphbusiness.banking.BankManagerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BankManagerTest.class,
        DAOTest.class
})
public class ManagerTest {
    @BeforeClass
    public static void setupClass() {
        try {
            DBConnector
                    .getFakeConnection()
                    .prepareStatement("call setup_fbanking()")
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        BankManagerHolder.manager = new BankManager(new DAO(DBConnector.getFakeConnection()));
    }
}
