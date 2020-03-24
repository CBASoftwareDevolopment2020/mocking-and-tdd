package banking;

import java.util.ArrayList;
import java.util.List;

public class RealCustomer implements Customer {

    private String cpr;
    private String name;
    private Bank bank;
    private List<Account> accounts = new ArrayList<>();

    public RealCustomer(Bank bank, String cpr, String name) {
        this.bank = bank;
        this.cpr = cpr;
        this.name = name;
    }

    @Override
    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    @Override
    public String getCpr() {
        return cpr;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void transfer(long amount, Account sourceAccount, Customer targetCustomer) {
        Account targetAccount = targetCustomer.getFirstAccount();
        sourceAccount.transfer(amount, targetAccount);
    }

    @Override
    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public Account getFirstAccount() {
        return accounts.get(0);
    }
}
