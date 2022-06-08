package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Coppia;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer,Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID";
		Map<Integer, Match> result = new HashMap<Integer, Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.put(match.getMatchID(), match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Match> getVertici(Map<Integer, Match> idMap, int mese){
		
		String sql = "select m.`MatchID` as id "
				+ "from matches m "
				+ "where MONTH(m.`Date`)= ?";
		
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				result.add(idMap.get(res.getInt("id")));

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public List<Coppia> getArchi(Map<Integer, Match> idMap, int mese, int time){
		
		String sql = "select  m1.`MatchID` as m1, m2.`MatchID` as m2, count(*) as peso "
				+ "from matches m1, matches m2, actions a1, actions a2 "
				+ "where MONTH(m1.`Date`) = ? and MONTH(m2.`Date`) = ? "
				+ "and a1.`PlayerID`= a2.`PlayerID` and a1.`MatchID`<> a2.`MatchID` "
				+ "and a1.`MatchID`= m1.`MatchID` and m2.`MatchID`= a2.`MatchID` "
				+ "and a1.`TimePlayed`>=? "
				+ "and a2.`TimePlayed`>=? "
				+ "and m1.`MatchID`> m2.`MatchID` "
				+ "group by  m1.`MatchID`, m2.`MatchID`";
		
		List<Coppia> result = new ArrayList<Coppia>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			st.setInt(2, mese);
			st.setInt(3, time);
			st.setInt(4, time);
			ResultSet res = st.executeQuery();
			
			while (res.next()) {

				result.add(new Coppia(idMap.get(res.getInt("m1")), idMap.get(res.getInt("m2")), res.getInt("peso")));

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}
