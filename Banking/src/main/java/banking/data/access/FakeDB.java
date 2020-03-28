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

public class FakeDB implements IDAO {

    private List<Customer> customers;
    private List<Account> accounts;
    private List<Bank> banks;
    private List<Movement> movements;

    public FakeDB(){

        /* init arrays */
        this.customers = new ArrayList<Customer>();
        this.accounts = new ArrayList<Account>();
        this.banks = new ArrayList<Bank>();
        this.movements = new ArrayList<Movement>();

        /* Bank */
        Bank bank = new RealBank("bid123", "Slybank");
        banks.add(bank);

        /* Customers */
        Customer cus1 = new RealCustomer(bank, "id123", "Jacob");
        Customer cus2 = new RealCustomer(bank, "id321", "Nikolaj");
        Customer cus3 = new RealCustomer(bank, "id420", "Stephan");
        customers.add(cus1);
        customers.add(cus2);
        customers.add(cus3);

        /* Accounts */
        Account acc1 = new RealAccount(bank, cus1, "42069");
        Account acc2 = new RealAccount(bank, cus1, "666");
        accounts.add(acc1);
        accounts.add(acc2);

        /* Movements */
        Movement mov1 = new RealMovement(acc1, acc2, 420, 1585409738);
        Movement mov2 = new RealMovement(acc1, acc2, 80, 1585409750);
        Movement mov3 = new RealMovement(acc2, acc1, 300, 1585409780);
        movements.add(mov1);
        movements.add(mov2);
        movements.add(mov3);
    }

    @Override
    public AccountDTO getAccount(String id) {
        for (int i = 0; i < accounts.size(); i++) {

            Account acc = accounts.get(i);

            if (acc.getNumber() == id) {
                return new AccountDTO(acc.getNumber(), acc.getCustomer().getCpr(), acc.getBank().getCvr());
            }
        }
        return null;
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accounts
                .stream()
                .map(acc -> new AccountDTO(acc.getNumber(), acc.getCustomer().getCpr(), acc.getBank().getCvr()))
                .collect(Collectors.toList());
    }

    @Override
    public int addAccount(String accountNumber, String customerCpr) {
        /* check if account exists */
        for (Account acc: accounts) {
            if(acc.getNumber() == accountNumber){
                return 0;
            }
        }

        /* check if customer exists */
        for (Customer cus: customers) {
            if(cus.getCpr() == customerCpr){
                accounts.add(new RealAccount(banks.get(0), cus, accountNumber));
                return 1;
            }
        }

        return 0;
    }

    @Override
    public List<AccountDTO> getAccountsFromCustomer(String customerCPR) {

        return accounts
                .stream()
                .filter(acc -> acc.getCustomer().getCpr() == customerCPR)
                .map(acc -> new AccountDTO(acc.getNumber(), acc.getCustomer().getCpr(), acc.getBank().getCvr()))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomer(String id) {
        for (int i = 0; i < customers.size(); i++) {

            Customer cus = customers.get(i);

            if (cus.getCpr() == id) {
                return new CustomerDTO(id, cus.getName());
            }
        }
        return null;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customers
                .stream()
                .map(cus -> new CustomerDTO(cus.getCpr(), cus.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public int addCustomer(String cpr, String name, String cvr) {

        /* Check if customer exists */
        for (Customer cus: customers) {
            if(cus.getCpr() == cpr){
                return 0;
            }
        }

        customers.add(new RealCustomer(banks.get(0), cpr, name));
        return 1;
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

        return movements
                .stream()
                .filter(mov -> mov.getTarget().getNumber() == accountNumber)
                .map(mov -> new MovementDTO(mov.getTime(), mov.getAmount(), mov.getSource().getNumber(), mov.getTarget().getNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovementDTO> getWithdrawals(String accountNumber) {
        return movements
                .stream()
                .filter(mov -> mov.getSource().getNumber() == accountNumber)
                .map(mov -> new MovementDTO(mov.getTime(), mov.getAmount(), mov.getSource().getNumber(), mov.getTarget().getNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public int addMovement(long amount, String sourceNumber, String targetNumber) {

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
            movements.add(new RealMovement(source, target, amount, 1585409766));
            return 1;
        }
        return 0;
    }
}
