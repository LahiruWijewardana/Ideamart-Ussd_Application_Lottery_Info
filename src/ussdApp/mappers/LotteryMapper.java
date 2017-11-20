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
	
	public ArrayList<String> showMatches() throws SQLException{

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String query = "SELECT matchDetail from matchScores";
        
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(query);
        
        ArrayList<String> matchScoresList = new ArrayList<String>();

        while (rs.next()) {
        		matchScoresList.add(rs.getString("matchDetail"));
        }

        con.close();

        return matchScoresList;
    }
	
	public String getScore(String match) throws SQLException{
		
        String score = null;

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String sql = "SELECT score from matchScores Where matchDetail = ?";
        
     // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setString(1, match);
        
        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
        		score = rs.getString("score");
        }

        con.close();

        return score;
    }
	
	public void setCounts(ArrayList<Lottery> lotteryList) throws SQLException{

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String query = "SELECT * from lottery";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(query);

        while (rs.next()) {
        		for (int i = 0; i < lotteryList.size(); i++) {
        			
        			Lottery lottery = lotteryList.get(i);
        			
        			if (rs.getString("lotteryName").equalsIgnoreCase(lottery.getLotteryName())) {
        				lottery.setNumCount(rs.getInt("numCount"));
        				lottery.setLetterCount(rs.getInt("letterCount"));
        				lottery.setBonusNumCount(rs.getInt("bonusNumCount"));
        				lottery.setLotteryName(rs.getString("lotteryName"));
        			}
        		}
        }

        con.close();

        
    }
	
	public void updateLottery (Lottery lottery) throws SQLException{

        DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String sql = "UPDATE lottery SET drawNumber = ?, lotterynumbers = ?, lotteryLetter = ?, lotteryBonus = ? WHERE lotteryName = ?";
        
     // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setInt(1, lottery.getDrawNumber());
        preparedStmt.setString(2, lottery.getLotteryNumbers());
        preparedStmt.setString(3, lottery.getLotteryLetter());
        preparedStmt.setString(4, lottery.getLotteryBonus());
        preparedStmt.setString(5, lottery.getLotteryName());
        
        preparedStmt.execute();

        con.close();
	}

}
