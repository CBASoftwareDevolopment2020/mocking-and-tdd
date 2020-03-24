package banking.data.access;

import banking.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MovementDAO {
    public static List<Movement> getDeposits(String accountNumber) {
        List<Movement> movements = new ArrayList<>();

        try {
            Connection con = DBConnector.getConnection();
            PreparedStatement stm = con.prepareStatement("SELECT * FROM movement WHERE target = ?)");
            stm.setString(1, accountNumber);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                long amount = rs.getLong(2);
                long time = rs.getLong(3);
                String target = rs.getString(4);
                String source = rs.getString(5);

                Account sourceAccount = AccountDAO.get(source);
                Account targetAccount = AccountDAO.get(target);
                movements.add(new RealMovement(sourceAccount, targetAccount, amount, time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movements;
    }

    public static List<Movement> getWithdrawals(String accountNumber) {
        List<Movement> movements = new ArrayList<>();

        try {
            Connection con = DBConnector.getConnection();
            PreparedStatement stm = con.prepareStatement("SELECT * FROM movement WHERE source = ?)");
            stm.setString(1, accountNumber);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                long amount = rs.getLong(2);
                long time = rs.getLong(3);
                String target = rs.getString(4);
                String source = rs.getString(5);

                Account sourceAccount = AccountDAO.get(source);
                Account targetAccount = AccountDAO.get(target);
                movements.add(new RealMovement(sourceAccount, targetAccount, amount, time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movements;
    }

    public static int add(long amount, String sourceNumber, String targetNumber) {
        try {
            Connection con = DBConnector.getConnection();
            PreparedStatement stm = con.prepareStatement("CALL make_transfer(?, ?, ?)");
            stm.setLong(1, amount);
            stm.setString(2, sourceNumber);
            stm.setString(3, targetNumber);
            return stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
