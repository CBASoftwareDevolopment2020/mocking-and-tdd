package banking;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class MovementTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void testGetters() {
        Account source = context.mock(Account.class, "source");
        Account target = context.mock(Account.class, "target");
        long amount = 100_00;
        long time = new Date().getTime();
        Movement movement = new RealMovement(source, target, amount, time);

        assertEquals(source, movement.getSource());
        assertEquals(target, movement.getTarget());
        assertEquals(amount, movement.getAmount());
        assertEquals(time, movement.getTime());
    }
}
