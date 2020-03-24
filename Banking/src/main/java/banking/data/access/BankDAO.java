package banking.data.access;

import banking.Bank;
import banking.RealBank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BankDAO {
    public static Bank get(String id) {
        try {
            Connection con = DBConnector.getConnection();

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

    public static List<Bank> getAll() {
        List<Bank> banks = new ArrayList<Bank>();

        try {
            Connection con = DBConnector.getConnection();
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

    public static int add(String cvr, String name) {
        try {
            Connection con = DBConnector.getConnection();
            PreparedStatement stm = con.prepareStatement("CALL add_bank(?, ?)");
            stm.setString(1, cvr);
            stm.setString(2, name);
            return stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
