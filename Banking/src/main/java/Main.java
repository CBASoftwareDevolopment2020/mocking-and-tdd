import banking.data.access.FakeDB;

public class Main {
    public static void main(String[] args) {
        //Bank bank = BankDAO.add("bank456", "danielsBank");

        // BankDAO.get("bank123");
        //System.out.println(bank.getName());
        //System.out.println(bank.getCvr());

        //List<Bank> banks = BankDAO.getAll();
        //System.out.println(banks.size());
        System.out.println("Stephan");
        FakeDB fdb = new FakeDB();
        System.out.println(fdb.getAllAccounts().size());
        System.out.println(fdb);
    }
}
