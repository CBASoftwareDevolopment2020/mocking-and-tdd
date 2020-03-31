package banking;

import banking.data.access.FakeDB;
import dk.cphbusiness.banking.BankManagerHolder;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import dk.cphbusiness.banking.BankManagerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BankManagerTest.class
})
public class FakeManagerTest {
    @BeforeClass
    public static void setupClass() {
        BankManagerHolder.manager = new BankManager(new FakeDB());
    }

}
