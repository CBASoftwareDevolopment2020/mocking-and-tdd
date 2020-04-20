package banking.data.access;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {

    private static Connection conn;
    private static Connection fconn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.postgresql.Driver");
                String url = String.format("jdbc:postgresql://%s:%s/%s", Env.dbhost, Env.dbport, Env.database );
                conn = DriverManager.getConnection(url, Env.dbuser, Env.dbpassword);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return conn;
    }

    public static Connection getFakeConnection() {
        if (fconn == null) {
            try {
                Class.forName("org.postgresql.Driver");
                String url = String.format("jdbc:postgresql://%s:%s/%s", Env.dbhost, Env.dbport, Env.fdatabase );
                fconn = DriverManager.getConnection(url, Env.dbuser, Env.dbpassword);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return fconn;
    }
}