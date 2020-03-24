package banking;

import java.util.List;

public interface Account {
    long getBalance();
    Bank getBank();
    Customer getCustomer();
    List<Movement> getDeposits();
    String getNumber();
    List<Movement> getWithdrawals();
    void transfer(long amount, Account targetAccount);
    void transfer(long amount, String targetCustomer);

    void addDeposit(Movement deposit);

    void addWithdrawal(Movement withdrawal);
}