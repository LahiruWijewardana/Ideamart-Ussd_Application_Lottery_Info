package ussdApp.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ussdApp.dbconnect.DatabaseConnect;
import ussdApp.model.Users;

public class UsersMapper {
	
	public Boolean findUser(Users user) throws SQLException{
        Boolean userFound = false;

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String query = "SELECT msisdn from Users";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(query);

        ArrayList<String> msisdnArray = new ArrayList<String>();

        while (rs.next()) {
            msisdnArray.add(rs.getString("msisdn"));

        }

        if (msisdnArray.contains(user.getMsisdn())){
            userFound = true;
        }

        con.close();

        return userFound;
    }
	
	public Boolean findRegister(Users user) throws SQLException{
        Boolean userRegistered = false;

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String sql = "SELECT register from Users Where msisdn = ?";
        
     // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setString(1, user.getMsisdn());
        
        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
        		if (rs.getString("register").equalsIgnoreCase("1")){
        			userRegistered = true;
            }

        }

        con.close();

        return userRegistered;
    }
	
	public int findStatus(Users user) throws SQLException{
        int usersStatus = 0;

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String sql = "SELECT status from Users Where msisdn = ?";
        
     // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setString(1, user.getMsisdn());
        
        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
        		usersStatus = rs.getInt("status");

        }

        con.close();

        return usersStatus;
    }
	
	public void addUser(Users user) throws SQLException {

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String sql = "INSERT INTO Users (msisdn, register, status) VALUES (?,?, 0)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setString(1, user.getMsisdn());
        preparedStmt.setString(2, user.getType());

        System.out.println(preparedStmt);
        preparedStmt.execute();
        con.close();

    }
	
	public void subcribeUser(Users user) throws SQLException {

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String sql = "UPDATE Users SET register = ? WHERE msisdn = ?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setString(1, "1");
        preparedStmt.setString(2, user.getMsisdn());

        System.out.println(preparedStmt);
        preparedStmt.execute();
        con.close();

    }
	
	public void updateUserStatus(Users user) throws SQLException {

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String sql = "UPDATE Users SET status = ? WHERE msisdn = ?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setInt(1, user.getStatus());
        preparedStmt.setString(2, user.getMsisdn());

        System.out.println(preparedStmt);
        preparedStmt.execute();
        con.close();

    }
	
	public Users getUser(Users user) throws SQLException{

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String sql = "SELECT * FROM Users WHERE msisdn = ?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setString(1, user.getMsisdn());
        
        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            user.setType(rs.getString("register"));
            user.setStatus(rs.getInt("status"));
        }

        con.close();

        return user;
    }

}
