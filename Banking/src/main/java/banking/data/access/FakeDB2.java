package banking.data.access;

import banking.*;
import dk.cphbusiness.banking.AccountDTO;
import dk.cphbusiness.banking.BankDTO;
import dk.cphbusiness.banking.CustomerDTO;
import dk.cphbusiness.banking.MovementDTO;
import net.sourceforge.plantuml.real.Real;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeDB2 implements IDAO {

    //private List<Customer> customers;
    //private List<Account> accounts;
    //private List<Movement> movements;
    private List<Bank> banks;

    public FakeDB2(){

        /* Bank */
        Bank bank = new RealBank("bid123", "Slybank");
        banks.add(bank);

        /* Customers */
        Customer cus1 = new RealCustomer(bank, "id123", "Jacob");
        Customer cus2 = new RealCustomer(bank, "id321", "Nikolaj");
        Customer cus3 = new RealCustomer(bank, "id420", "Stephan");

        /* Accounts */
        Account acc1 = new RealAccount(bank, cus1, "42069");
        Account acc2 = new RealAccount(bank, cus1, "666");

        /* Movements */
        Movement mov1 = new RealMovement(acc1, acc2, 420, 1585409738);
        Movement mov2 = new RealMovement(acc1, acc2, 80, 1585409750);
        Movement mov3 = new RealMovement(acc2, acc1, 300, 1585409780);
    }

    @Override
    public AccountDTO getAccount(String id) {

            Account acc = banks.get(0).getAccount(id);
            if(acc == null) return null;
            else return new AccountDTO(acc.getNumber(), acc.getCustomer().getCpr(), acc.getBank().getCvr());

    }

    @Override
    public List<AccountDTO> getAllAccounts() {

        List<Account> accounts = banks.get(0).getAccounts();
        return accounts
                .stream()
                .map(acc -> new AccountDTO(acc.getNumber(), acc.getCustomer().getCpr(), acc.getBank().getCvr()))
                .collect(Collectors.toList());
    }

    @Override
    public int addAccount(String accountNumber, String customerCpr) {

        Bank bank = banks.get(0);
        Account acc = bank.getAccount(accountNumber);
        Customer cus = bank.getCustomer(customerCpr);

        if(acc == null || cus == null){
            return 0;
        }else{
            bank.registerAccount(new RealAccount(bank, cus, accountNumber));
            return 1;
        }
    }

    @Override
    public List<AccountDTO> getAccountsFromCustomer(String customerCPR) {
        Bank bank = banks.get(0);
        Customer cus = bank.getCustomer(customerCPR);

        if (cus == null) return new ArrayList<AccountDTO>();

        return bank
                .getAccounts(cus)
                .stream()
                .filter(acc -> acc.getCustomer().getCpr() == customerCPR)
                .map(acc -> new AccountDTO(acc.getNumber(), acc.getCustomer().getCpr(), acc.getBank().getCvr()))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomer(String id) {

        Bank bank = banks.get(0);
        Customer cus = bank.getCustomer(id);

        if (cus == null) return null;

        return new CustomerDTO(cus.getCpr(), cus.getName());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        return banks
                .get(0)
                .getCustomers()
                .stream()
                .map(cus -> new CustomerDTO(cus.getCpr(), cus.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public int addCustomer(String cpr, String name, String cvr) {


        Bank bank = banks.get(0);
        Customer cus = bank.getCustomer(cpr);

        if(cus == null){
            return 0;
        }else{
            bank.registerAccount(new RealAccount(bank, cus, cvr));
            return 1;
        }
    }

    @Override
    public BankDTO getBank(String id) {
        for (int i = 0; i < banks.size(); i++) {

            Bank bank = banks.get(i);

            if (bank.getCvr() == id) {
                return new BankDTO(bank.getCvr(), bank.getName());
            }
        }
        return null;
    }

    @Override
    public List<BankDTO> getAllBanks() {
        return banks
                .stream()
                .map(bank -> new BankDTO(bank.getCvr(), bank.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public int addBank(String cvr, String name) {

        /* Check if bank exists */
        for (Bank bank: banks) {
            if(bank.getCvr() == cvr){
                return 0;
            }
        }

        banks.add(new RealBank(cvr, name));
        return 1;
    }

    @Override
    public List<MovementDTO> getDeposits(String accountNumber) {
        Account acc = banks.get(0).getAccount(accountNumber);
        if(acc == null) return new ArrayList<MovementDTO>();

        return acc
                .getDeposits()
                .stream()
                .map(mov -> new MovementDTO(mov.getTime(), mov.getAmount(), mov.getSource().getNumber(), mov.getTarget().getNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovementDTO> getWithdrawals(String accountNumber) {
        Account acc = banks.get(0).getAccount(accountNumber);
        if(acc == null) return new ArrayList<MovementDTO>();

        return acc
                .getWithdrawals()
                .stream()
                .map(mov -> new MovementDTO(mov.getTime(), mov.getAmount(), mov.getSource().getNumber(), mov.getTarget().getNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public int addMovement(long amount, String sourceNumber, String targetNumber) {

        Bank bank = banks.get(0);
        List<Account> accounts = bank.getAccounts();

        Account source = null;
        Account target = null;

        for (Account acc: accounts) {
            if (acc.getNumber() == sourceNumber) {
                source = acc;
            }
            if (acc.getNumber() == targetNumber) {
                target = acc;
            }
        }

        if(source != null && target != null && source != target){
            Movement mov = new RealMovement(source, target, amount, 1585409766);
            source.addDeposit(mov);
            target.addWithdrawal(mov);
            return 1;
        }
        return 0;
    }
}
