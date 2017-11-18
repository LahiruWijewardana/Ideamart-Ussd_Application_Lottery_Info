package ussdApp.dbconnect;

import java.sql.*;
import ussdApp.util.Utility;

public class DatabaseConnect {
	Connection con;

    public Connection dbConnect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Utility.url, Utility.username, Utility.password);

            System.out.println("Database connected!");

            return con;

        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
