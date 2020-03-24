package banking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RealAccount implements Account {
    private List<Movement> deposits = new ArrayList<>();
    private List<Movement> withdrawals = new ArrayList<>();
    private Bank bank;
    private String number;
    private Customer customer;

    public RealAccount(Bank bank, Customer customer, String number) {
        this.bank = bank;
        this.customer = customer;
        this.number = number;
    }

    @Override
    public long getBalance() {
        long in = deposits
                .stream()
                .mapToLong(e -> e.getAmount())
                .reduce(0, (acc, e) -> acc + e);
        long out = withdrawals
                .stream()
                .mapToLong(e -> e.getAmount())
                .reduce(0, (acc, e) -> acc + e);
        return in - out;
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public List<Movement> getDeposits() {
        return deposits;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public List<Movement> getWithdrawals() {
        return withdrawals;
    }

    @Override
    public void transfer(long amount, Account targetAccount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than 0");
        Movement movement = new RealMovement(this, targetAccount, amount, new Date().getTime());
        this.addWithdrawal(movement);
        targetAccount.addDeposit(movement);
    }

    @Override
    public void transfer(long amount, String targetCustomer) {
        Customer target = this.bank.getCustomer(targetCustomer);
        Account targetAccount = target.getFirstAccount();
        transfer(amount, targetAccount);
    }

    @Override
    public void addDeposit(Movement deposit) {
        deposits.add(deposit);
    }

    @Override
    public void addWithdrawal(Movement withdrawal) {
        withdrawals.add(withdrawal);
    }
}
