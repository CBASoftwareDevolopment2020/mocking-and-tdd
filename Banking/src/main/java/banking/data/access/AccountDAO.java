package banking.data.access;

import banking.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public static Account get(String id) {
        try {
            Connection con = DBConnector.getConnection();

            PreparedStatement stm = con.prepareStatement("SELECT * FROM get_account(?)");
            stm.setString(1, id);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                String number = rs.getString(1);
                String customer_cpr = rs.getString(2);

                Customer customer = CustomerDAO.get(customer_cpr);
                return new RealAccount(customer.getBank(), customer, number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Account> getAll() {
        List<Account> accounts = new ArrayList<Account>();

        try {
            Connection con = DBConnector.getConnection();
            PreparedStatement stm = con.prepareStatement("SELECT * FROM accounts");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String number = rs.getString(1);
                String customerCPS = rs.getString(2);

                Customer customer = CustomerDAO.get(customerCPS);
                accounts.add(new RealAccount(customer.getBank(), customer, number));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static int add(String accountNumber, String customerCpr) {
        try {
            Connection con = DBConnector.getConnection();
            PreparedStatement stm = con.prepareStatement("CALL add_account(?, ?)");
            stm.setString(1, accountNumber);
            stm.setString(2, customerCpr);
            return stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Account> getAccountsFromCustomer(String customerCPR){
        List<Account> accounts = new ArrayList<Account>();

        try {
            Connection con = DBConnector.getConnection();
            PreparedStatement stm = con.prepareStatement("SELECT * FROM accounts WHERE fk_customer_cpr = ?");
            stm.setString(1, customerCPR);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String number = rs.getString(1);
                String customerCPS = rs.getString(2);

                Customer customer = CustomerDAO.get(customerCPS);
                accounts.add(new RealAccount(customer.getBank(), customer, number));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
