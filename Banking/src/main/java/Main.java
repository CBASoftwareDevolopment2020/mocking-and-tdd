import banking.Bank;
import banking.RealBank;
import banking.data.access.BankDAO;
import dk.cphbusiness.banking.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Bank bank = new RealBank("bank456", "danielsBank");
        bank = BankDAO.add(bank);

        // BankDAO.get("bank123");
        System.out.println(bank.getName());
        System.out.println(bank.getCvr());

        List<Bank> banks = BankDAO.getAll();
        System.out.println(banks.size());
    }
}
