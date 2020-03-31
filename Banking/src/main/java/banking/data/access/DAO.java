package banking.data.access;

import banking.Account;
import banking.Bank;
import banking.Customer;
import banking.Movement;
import dk.cphbusiness.banking.AccountDTO;
import dk.cphbusiness.banking.BankDTO;
import dk.cphbusiness.banking.CustomerDTO;
import dk.cphbusiness.banking.MovementDTO;

import java.util.List;

public class DAO implements IDAO{

    @Override
    public Account getAccount(String id) {
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        return null;
    }

    @Override
    public int addAccount(String accountNumber, String customerCpr) {
        return 0;
    }

    @Override
    public List<Account> getAccountsFromCustomer(String customerCPR) {
        return null;
    }

    @Override
    public Customer getCustomer(String id) {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public int addCustomer(String cpr, String name, String cvr) {
        return 0;
    }

    @Override
    public Bank getBank(String id) {
        return null;
    }

    @Override
    public List<Bank> getAllBanks() {
        return null;
    }

    @Override
    public int addBank(String cvr, String name) {
        return 0;
    }

    @Override
    public List<Movement> getDeposits(String accountNumber) {
        return null;
    }

    @Override
    public List<Movement> getWithdrawals(String accountNumber) {
        return null;
    }

    @Override
    public int addMovement(long amount, String sourceNumber, String targetNumber) {
        return 0;
    }
}
