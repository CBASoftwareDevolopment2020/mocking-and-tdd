package banking;

import java.util.Date;

public class RealMovement implements Movement {
    private Account source;
    private Account target;
    private long amount;
    private long time;

    public RealMovement(Account source, Account target, long amount, long timestamp) {
        this.source = source;
        this.target = target;
        this.amount = amount;
        this.time = timestamp;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public Account getSource() {
        return source;
    }

    @Override
    public Account getTarget() {
        return target;
    }

    @Override
    public long getTime() {
        return time;
    }
}
