package ussdApp.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ussdApp.dbconnect.DatabaseConnect;
import ussdApp.model.Lottery;

public class LotteryMapper {
	
	public ArrayList<String> showLotteries() throws SQLException{

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String query = "SELECT lotteryName from lottery";
        
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(query);
        
        ArrayList<String> lotteryDetails = new ArrayList<String>();

        while (rs.next()) {
        		lotteryDetails.add(rs.getString("lotteryName"));
        }

        con.close();

        return lotteryDetails;
    }
	
	public Lottery getLotteryDetails(Lottery lottery) throws SQLException{

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String sql = "SELECT * from lottery Where lotteryName = ?";
        
     // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setString(1, lottery.getLotteryName());
        
        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
        		lottery.setDrawNumber(rs.getInt("drawNumber"));
        		lottery.setNumCount(rs.getInt("numCount"));
        		lottery.setLetterCount(rs.getInt("letterCount"));
        		lottery.setBonusNumCount(rs.getInt("bonusNumCount"));
        		lottery.setHasSymbol(rs.getBoolean("hasSymbol"));
        		lottery.setLotteryNumbers(rs.getString("lotterynumbers"));
        		lottery.setLotteryLetter(rs.getString("lotteryLetter"));
        		lottery.setLotteryBonus(rs.getString("lotteryBonus"));
        		lottery.setLotterySymbol(rs.getString("lotterySymbol"));
        }

        con.close();

        return lottery;
    }
	
	public Lottery setCounts(Lottery lottery) throws SQLException{

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String query = "SELECT * from lottery";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(query);

        while (rs.next()) {
        			
    			if (rs.getString("lotteryName").equalsIgnoreCase(lottery.getLotteryName())) {
    				
    				if (lottery.getDrawNumber() <= rs.getInt("drawNumber")) {
    					lottery.setLatest(true);
    				} else {
    					lottery.setNumCount(rs.getInt("numCount"));
        				lottery.setLetterCount(rs.getInt("letterCount"));
        				lottery.setBonusNumCount(rs.getInt("bonusNumCount"));
        				lottery.setLotteryName(rs.getString("lotteryName"));
        				lottery.setHasSymbol(rs.getBoolean("hasSymbol"));
        				
        				lottery.setLatest(false);
    				}        				
    			}
        		
        }

        con.close();
        return lottery;
        
    }
	
	public void updateLottery (Lottery lottery) throws SQLException{

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String sql = "UPDATE lottery SET drawNumber = ?, lotterynumbers = ?, lotteryLetter = ?, lotteryBonus = ?, lotterySymbol = ? WHERE lotteryName = ?";
        
     // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setInt(1, lottery.getDrawNumber());
        preparedStmt.setString(2, lottery.getLotteryNumbers());
        preparedStmt.setString(3, lottery.getLotteryLetter());
        preparedStmt.setString(4, lottery.getLotteryBonus());
        preparedStmt.setString(5, lottery.getLotterySymbol());
        preparedStmt.setString(6, lottery.getLotteryName());
        
        preparedStmt.execute();

        con.close();
	}
	
	public ArrayList<Lottery> getNLBLotteries() throws SQLException {
		
		DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String query = "SELECT * FROM lottery WHERE IdValue IS NOT NULL";
        
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(query);
        
        ArrayList<Lottery> lotteryList = new ArrayList<Lottery>();
        
        while (rs.next()) {
    			Lottery lottery = new Lottery();
    			
    			lottery.setLotteryName(rs.getString("lotteryName"));
    			lottery.setIdValue(rs.getInt("IdValue"));
    			lottery.setDrawNumber(rs.getInt("drawNumber"));
    			
    			lotteryList.add(lottery);
        }
		
		return lotteryList;
	}

}
