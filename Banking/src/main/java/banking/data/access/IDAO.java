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

    AccountDTO getAccount(String id);
    List<AccountDTO> getAllAccounts();
    int addAccount(String accountNumber, String customerCpr);
    List<AccountDTO> getAccountsFromCustomer(String customerCPR);

    CustomerDTO getCustomer(String id);
    List<CustomerDTO> getAllCustomers();
    int addCustomer(String cpr, String name, String cvr);

    BankDTO getBank(String id);
    List<BankDTO> getAllBanks();
    int addBank(String cvr, String name);

    List<MovementDTO> getDeposits(String accountNumber);
    List<MovementDTO> getWithdrawals(String accountNumber);
    int addMovement(long amount, String sourceNumber, String targetNumber);

}
