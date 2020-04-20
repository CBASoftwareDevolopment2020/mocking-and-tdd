package banking;

import banking.data.access.AccountDAO;
import banking.data.access.BankDAO;
import banking.data.access.CustomerDAO;
import banking.data.access.IDAO;
import dk.cphbusiness.banking.*;

import java.util.List;
import java.util.stream.Collectors;

public class BankManager implements dk.cphbusiness.banking.BankManager {

    IDAO dao;

    public BankManager(IDAO dao) {
        this.dao = dao;
    }

    @Override
    public AccountDTO addAccount(String number, String cpr, String bankCVR){

        if(dao.addAccount(number, cpr) > 0){
            return new AccountDTO(number, cpr, bankCVR);
        }else{
            return null;
        }
    }

    @Override
    public CustomerDTO addCustomer(String cpr, String name, String accountNumber, String cvr) {


        if(dao.addCustomer(cpr, name, cvr) > 0){
            if(dao.addAccount(accountNumber, cpr) > 0){
                return new CustomerDTO(cpr, name);
            }
        }
        return null;
    }

    @Override
    public AccountDTO getAccount(String number) {
        Account acc = dao.getAccount(number);
        if(acc != null){
            return new AccountDTO(acc.getNumber(), acc.getCustomer().getCpr(), acc.getBank().getCvr());
        }
        return null;
    }

    @Override
    public List<AccountDTO> getCustomersAccounts(String customerId) {
        return dao
                .getAccountsFromCustomer(customerId)
                .stream()
                .map(a -> new AccountDTO(a.getNumber(), a.getCustomer().getCpr(), a.getBank().getCvr()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAccounts() {
        return dao
                .getAllAccounts()
                .stream()
                .map(a -> new AccountDTO(a.getNumber(), a.getCustomer().getCpr(), a.getBank().getCvr()))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomer(String customerId) {
        Customer cus = dao.getCustomer(customerId);
        return new CustomerDTO(cus.getCpr(), cus.getName());
    }

    @Override
    public List<CustomerDTO> getCustomers(String bankId) {
        return dao
                .getAllCustomers()
                .stream()
                .map(c -> new CustomerDTO(c.getCpr(), c.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public MovementDTO transfer(String sourceAccountId, String targetAccountId, long amount) {

        if(dao.addMovement(amount, sourceAccountId, targetAccountId) > 0){
            return new MovementDTO(System.currentTimeMillis() / 1000L, amount, sourceAccountId, targetAccountId);
        }else{
            return null;
        }
    }
}
