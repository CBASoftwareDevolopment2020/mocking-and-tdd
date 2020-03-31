package banking.data.access;

import banking.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAO implements IDAO{
    private Connection con;
    public DAO(Connection con) {
         this.con = con; /*DBConnector.getConnection();*/
    }

    @Override
    public Account getAccount(String id) {
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM get_account(?)");
            stm.setString(1, id);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                String number = rs.getString(1);
                String customer_cpr = rs.getString(2);

                Customer customer = getCustomer(customer_cpr);
                return new RealAccount(customer.getBank(), customer, number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<Account>();

        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM accounts");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String number = rs.getString(1);
                String customerCPS = rs.getString(2);

                Customer customer = getCustomer(customerCPS);
                accounts.add(new RealAccount(customer.getBank(), customer, number));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public int addAccount(String accountNumber, String customerCpr) {
        try {
            PreparedStatement stm = con.prepareStatement("CALL add_account(?, ?)");
            stm.setString(1, accountNumber);
            stm.setString(2, customerCpr);
            stm.executeUpdate();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Account> getAccountsFromCustomer(String customerCPR) {
        List<Account> accounts = new ArrayList<Account>();

        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM accounts WHERE fk_customer_cpr = ?");
            stm.setString(1, customerCPR);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String number = rs.getString(1);
                String customerCPS = rs.getString(2);

                Customer customer = getCustomer(customerCPS);
                accounts.add(new RealAccount(customer.getBank(), customer, number));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Customer getCustomer(String id) {
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM get_customer(?)");
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                String cpr = rs.getString(1);
                String name = rs.getString(2);
                String bank_cvr = rs.getString(3);

                Bank bank = getBank(bank_cvr);
                return new RealCustomer(bank, cpr, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<Customer>();

        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM customers");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String cpr = rs.getString(1);
                String name = rs.getString(2);
                String bank_cvr = rs.getString(3);

                Bank bank = getBank(bank_cvr);
                customers.add(new RealCustomer(bank, cpr, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public int addCustomer(String cpr, String name, String cvr) {
        try {
            PreparedStatement stm = con.prepareStatement("CALL add_customer(?, ?, ?)");
            stm.setString(1, cpr);
            stm.setString(2, name);
            stm.setString(3, cvr);
            stm.executeUpdate();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Bank getBank(String id) {
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM get_bank(?)");
            stm.setString(1, id);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                String cvr = rs.getString(1);
                String name = rs.getString(2);

                return new RealBank(cvr, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Bank> getAllBanks() {
        List<Bank> banks = new ArrayList<Bank>();

        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM banks");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String cvr = rs.getString(1);
                String name = rs.getString(2);
                banks.add(new RealBank(cvr, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return banks;
    }

    @Override
    public int addBank(String cvr, String name) {
        try {
            PreparedStatement stm = con.prepareStatement("CALL add_bank(?, ?)");
            stm.setString(1, cvr);
            stm.setString(2, name);
            return stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Movement> getDeposits(String accountNumber) {
        List<Movement> movements = new ArrayList<>();

        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM movements WHERE target = ?");
            stm.setString(1, accountNumber);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                long amount = rs.getLong(2);
                long time = rs.getTime(3).getTime();
                String target = rs.getString(4);
                String source = rs.getString(5);

                Account sourceAccount = getAccount(source);
                Account targetAccount = getAccount(target);
                movements.add(new RealMovement(sourceAccount, targetAccount, amount, time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movements;
    }

    @Override
    public List<Movement> getWithdrawals(String accountNumber) {
        List<Movement> movements = new ArrayList<>();

        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM movements WHERE source = ?");
            stm.setString(1, accountNumber);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                long amount = rs.getLong(2);
                long time = rs.getTime(3).getTime();
                String target = rs.getString(4);
                String source = rs.getString(5);

                Account sourceAccount = getAccount(source);
                Account targetAccount = getAccount(target);
                movements.add(new RealMovement(sourceAccount, targetAccount, amount, time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movements;
    }

    @Override
    public int addMovement(long amount, String sourceNumber, String targetNumber) {
        try {
            PreparedStatement stm = con.prepareStatement("CALL make_transfer(?, ?, ?)");
            stm.setLong(1, amount);
            stm.setString(2, sourceNumber);
            stm.setString(3, targetNumber);
            stm.executeUpdate();
            return 420;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
