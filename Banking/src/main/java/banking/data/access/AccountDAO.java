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
                String customer_cpr = rs.getString(2);

                Customer customer = CustomerDAO.get(customer_cpr);
                accounts.add(new RealAccount(customer.getBank(), customer, number));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static Account add(Account account) {
        try {
            Connection con = DBConnector.getConnection();
            PreparedStatement stm = con.prepareStatement("CALL add_account(?, ?)");
            stm.setString(1, account.getNumber());
            stm.setString(2, account.getCustomer().getCpr());
            stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }
}
