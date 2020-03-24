package banking;

import java.util.List;

public interface Customer {
    List<Account> getAccounts();
    Bank getBank();
    String getCpr();
    String getName();
    void transfer(long amount, Account sourceAccount, Customer targetCustomer);

    void addAccount(Account expectedAccount);

    Account getFirstAccount();
}