package banking.data.access;

import banking.Bank;
import banking.RealBank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class BankDAO implements IDAO<Bank> {
    @Override
    public Bank get(String id) {
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

    @Override
    public List<Bank> getAll() {
        return null;
    }

    @Override
    public Bank save(Bank bank) {
        return null;
    }
}

/*
* Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT VERSION()")) {

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }
           }
* */