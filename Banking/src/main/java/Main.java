import banking.Bank;
import banking.data.access.BankDAO;
import dk.cphbusiness.banking.*;

public class Main {
    public static void main(String[] args) {

        BankDAO bankDAO = new BankDAO();
        Bank bank = bankDAO.get("bank123");

        System.out.println(bank.getName());
        System.out.println(bank.getCvr());
    }
}
