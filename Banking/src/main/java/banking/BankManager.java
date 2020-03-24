package banking;

import dk.cphbusiness.banking.*;
import java.util.List;

public class BankManager implements dk.cphbusiness.banking.BankManager {
    @Override
    public AccountDTO addAccount(String number, String cpr){
        throw new UnsupportedOperationException();
    }
    @Override
    public CustomerDTO addCustomer(String cpr, String name, String accountNumber, String bankId){
        throw new UnsupportedOperationException();
    }
    @Override
    public AccountDTO getAccount(String number){
        throw new UnsupportedOperationException();
    }
    @Override
    public List<AccountDTO> getCustomersAccounts(String customerId){
        throw new UnsupportedOperationException();
    }
    @Override
    public List<AccountDTO> getAccounts(String customerId){
        throw new UnsupportedOperationException();
    }
    @Override
    public CustomerDTO getCustomer(String customerId){
        throw new UnsupportedOperationException();
    }
    @Override
    public List<CustomerDTO> getCustomers(String bankId){
        throw new UnsupportedOperationException();
    }
    @Override
    public MovementDTO transfer(String sourceAccountId, String targetAccountId, long amount){
        throw new UnsupportedOperationException();
    }
}
