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

import static dk.cphbusiness.banking.BankManagerHolder.manager;

public interface IDAO {

    Account getAccount(String id);
    List<Account> getAllAccounts();
    int addAccount(String accountNumber, String customerCpr);
    List<Account> getAccountsFromCustomer(String customerCPR);

    Customer getCustomer(String id);
    List<Customer> getAllCustomers();
    int addCustomer(String cpr, String name, String cvr);

    Bank getBank(String id);
    List<Bank> getAllBanks();
    int addBank(String cvr, String name);

    List<Movement> getDeposits(String accountNumber);
    List<Movement> getWithdrawals(String accountNumber);
    int addMovement(long amount, String sourceNumber, String targetNumber);

}
