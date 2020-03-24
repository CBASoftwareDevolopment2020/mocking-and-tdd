package dk.cphbusiness.banking;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface BankManager {
    AccountDTO addAccount(String number, String cpr, String bankCVR);

    CustomerDTO addCustomer(String cpr, String name, String accountNumber, String bankCVR);

    AccountDTO getAccount(String number);

    List<AccountDTO> getCustomersAccounts(String customerId);

    List<AccountDTO> getAccounts();

    CustomerDTO getCustomer(String customerId);

    List<CustomerDTO> getCustomers(String bankId);

    MovementDTO transfer(String sourceAccountId, String targetAccountId, long amount);
}
