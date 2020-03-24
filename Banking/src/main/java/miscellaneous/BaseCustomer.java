package miscellaneous;

import banking.Account;
import banking.Bank;
import banking.Customer;

import java.util.List;

public class BaseCustomer implements Customer {
    public BaseCustomer(String number, String name) { }


    @Override
    public void transfer(long amount, Account sourceAccount, Customer targetCustomer) {

    }

    @Override
    public void addAccount(Account expectedAccount) {

    }

    @Override
    public Account getFirstAccount() {
        return null;
    }


    @Override
    public List<Account> getAccounts() {
        return null;
    }

    @Override
    public Bank getBank() {
        return null;
    }

    @Override
    public String getCpr() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}