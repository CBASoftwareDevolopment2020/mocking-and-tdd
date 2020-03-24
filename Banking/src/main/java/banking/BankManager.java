package banking;

import banking.data.access.AccountDAO;
import banking.data.access.BankDAO;
import banking.data.access.CustomerDAO;
import dk.cphbusiness.banking.*;

import java.util.List;

public class BankManager implements dk.cphbusiness.banking.BankManager {
    @Override
    public AccountDTO addAccount(String number, String cpr, String bankCVR) {
        AccountDAO.add(number, cpr);
        AccountDTO accountDTO = new AccountDTO(number, cpr, bankCVR);
        return accountDTO;
    }

    @Override
    public CustomerDTO addCustomer(String cpr, String name, String accountNumber, String cvr) {
        CustomerDAO.add(cpr, name, cvr);
        AccountDAO.add(accountNumber, cpr);
        return new CustomerDTO(cpr, name);
    }

    @Override
    public AccountDTO getAccount(String number) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AccountDTO> getCustomersAccounts(String customerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AccountDTO> getAccounts() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CustomerDTO getCustomer(String customerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CustomerDTO> getCustomers(String bankId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MovementDTO transfer(String sourceAccountId, String targetAccountId, long amount) {
        throw new UnsupportedOperationException();
    }
}
