package banking;

import java.util.Date;

public interface Movement {
    long getAmount();
    Account getSource();
    Account getTarget();
    long getTime();
}
