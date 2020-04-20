package banking.data.access;

import banking.*;
import dk.cphbusiness.banking.AccountDTO;
import dk.cphbusiness.banking.BankDTO;
import dk.cphbusiness.banking.CustomerDTO;
import dk.cphbusiness.banking.MovementDTO;
import net.sourceforge.plantuml.real.Real;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeDB implements IDAO {

    //private List<Customer> customers;
    //private List<Account> accounts;
    //private List<Movement> movements;
    private List<Bank> banks = new ArrayList<Bank>();

    public FakeDB(){

        /* Bank */
        Bank bank = new RealBank("bid123", "Slybank");
        banks.add(bank);

        /* Customers */
        Customer cus1 = new RealCustomer(bank, "id123", "Jacob");
        Customer cus2 = new RealCustomer(bank, "id321", "Nikolaj");
        Customer cus3 = new RealCustomer(bank, "id420", "Stephan");
        bank.registerCustomer(cus1);
        bank.registerCustomer(cus2);
        bank.registerCustomer(cus3);

        /* Accounts */
        Account acc1 = new RealAccount(bank, cus1, "42069");
        Account acc2 = new RealAccount(bank, cus1, "src123");
        Account acc3 = new RealAccount(bank, cus1, "trgt132");
        bank.registerAccount(acc1);
        bank.registerAccount(acc2);
        bank.registerAccount(acc3);
        cus1.addAccount(acc1);
        cus1.addAccount(acc2);
        cus1.addAccount(acc3);

        /* Movements */
        Movement mov1 = new RealMovement(acc1, acc2, 420, 1585409738);
        Movement mov2 = new RealMovement(acc1, acc2, 80, 1585409750);
        Movement mov3 = new RealMovement(acc2, acc1, 300, 1585409780);
    }

    @Override
    public Account getAccount(String id) {

            Account acc = banks.get(0).getAccount(id);
            if(acc == null) return null;
            else return acc;

    }

    @Override
    public List<Account> getAllAccounts() {

        List<Account> accounts = banks.get(0).getAccounts();
        return accounts;
    }

    @Override
    public int addAccount(String accountNumber, String customerCpr) {

        Bank bank = banks.get(0);
        Account acc = bank.getAccount(accountNumber);
        Customer cus = bank.getCustomer(customerCpr);

        if(acc != null || cus == null){
            return 0;
        }else{
            Account newAccount = new RealAccount(bank, cus, accountNumber);
            bank.registerAccount(newAccount);
            cus.addAccount(newAccount);
            return 1;
        }
    }

    @Override
    public List<Account> getAccountsFromCustomer(String customerCPR) {
        Bank bank = banks.get(0);
        Customer cus = bank.getCustomer(customerCPR);

        if (cus == null) return new ArrayList<Account>();

        return bank
                .getAccounts(cus)
                .stream()
                .filter(acc -> acc.getCustomer().getCpr().equals(customerCPR))
                .collect(Collectors.toList());
    }

    @Override
    public Customer getCustomer(String id) {

        Bank bank = banks.get(0);
        return bank.getCustomer(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return banks.get(0).getCustomers();
    }

    @Override
    public int addCustomer(String cpr, String name, String cvr) {


        Bank bank = banks.get(0);
        Customer cus = bank.getCustomer(cpr);

        if(cus != null){
            return 0;
        }else{
            bank.registerCustomer(new RealCustomer(bank,cpr,name));

            return 1;
        }
    }

    @Override
    public Bank getBank(String id) {
        for (int i = 0; i < banks.size(); i++) {

            Bank bank = banks.get(i);

            if (bank.getCvr().equals(id)) {
                return bank;
            }
        }
        return null;
    }

    @Override
    public List<Bank> getAllBanks() {
        return banks;
    }

    @Override
    public int addBank(String cvr, String name) {

        /* Check if bank exists */
        for (Bank bank: banks) {
            if(bank.getCvr().equals(cvr)){
                return 0;
            }
        }

        banks.add(new RealBank(cvr, name));
        return 1;
    }

    @Override
    public List<Movement> getDeposits(String accountNumber) {
        Account acc = banks.get(0).getAccount(accountNumber);
        if(acc == null) return new ArrayList<Movement>();

        return acc.getDeposits();
    }

    @Override
    public List<Movement> getWithdrawals(String accountNumber) {
        Account acc = banks.get(0).getAccount(accountNumber);
        if(acc == null) return new ArrayList<Movement>();

        return acc.getWithdrawals();
    }

    @Override
    public int addMovement(long amount, String sourceNumber, String targetNumber) {

        Bank bank = banks.get(0);
        List<Account> accounts = bank.getAccounts();

        Account source = null;
        Account target = null;

        for (Account acc: accounts) {
            if (acc.getNumber().equals(sourceNumber)) {
                source = acc;
            }
            if (acc.getNumber().equals(targetNumber)) {
                target = acc;
            }
        }

        if(source != null && target != null && source != target){
            Movement mov = new RealMovement(source, target, amount, System.currentTimeMillis() / 1000L);
            source.addDeposit(mov);
            target.addWithdrawal(mov);
            return 1;
        }
        return 0;
    }
}
