package dk.cphbusiness.banking;

import java.io.Serializable;

public class MovementDTO implements Serializable {
    private long id;
    private long time;
    private long amount;
    private String sourceAccount;
    private String targetAccount;

    public MovementDTO(long time, long amount, String sourceAccount, String targetAccount) {
        this.time = time;
        this.amount = amount;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
    }

    public long getTime() {
        return time;
    }

    public long getAmount() {
        return amount;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public long getId() {
        return id;
    }
}
