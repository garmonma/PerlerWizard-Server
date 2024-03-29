package com.nni.gamevate.network.gamedata.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nni.gamevate.network.gamedata.GameCharacter;

public class GameCharacterDAL {

	public GameCharacter getCharacter(Connection conn, Integer id){
		GameCharacter character = new GameCharacter();	
		ResultSet rs = executeIDQuery(conn, GameQueryConfig.GET_CHARACTER, id);
		
		try {
			while(rs.next()){
				character.setCharacterID(rs.getInt(1));
				character.setHealth(rs.getInt(2));
				character.setExperience(rs.getInt(3));
				character.setGold(rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return character;
	}
	
	public List<GameCharacter> getCharacters(Connection conn){
		
		List<GameCharacter> characters = new ArrayList<GameCharacter>();
		ResultSet rs = executeIDQuery(conn, GameQueryConfig.GET_CHARACTERS, 0);
		
		try {
			while(rs.next()){
				GameCharacter character = new GameCharacter();
				character.setCharacterID(rs.getInt(1));
				character.setHealth(rs.getInt(2));
				character.setExperience(rs.getInt(3));
				character.setGold(rs.getInt(4));
				characters.add(character);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//st.close();
				rs.close();
				//st = null;
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		return characters;
	}
	
	public boolean updateCharacter(Connection conn, GameCharacter gc) throws SQLException {
		if(conn == null){
			throw new NullPointerException("The Databse Connection is null!");
		}
		
		PreparedStatement st = null;
		
		try {
			conn.setAutoCommit(false);
			st = conn.prepareStatement(GameQueryConfig.UPDATE_CHARACTER);
			
			st.setInt(1, gc.getExperience());
			st.setInt(2, gc.getGold());
			st.setInt(3, gc.getHealth());
			st.setInt(4, gc.getCharacterID());
			st.executeUpdate();
			conn.commit();
			
		} catch(SQLException e){
			e.printStackTrace();
			if (conn != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                conn.rollback();
	            } catch(SQLException excep) {
	                e.printStackTrace();
	            }
	        }
			return false;
		} finally {
			if (st != null) {
	            st.close();
	        }
			
	        conn.setAutoCommit(true);
		}
		
		return true;
		
	}
	
	private ResultSet executeIDQuery(Connection conn, String query, int ID){
		if(conn == null){
			throw new NullPointerException("The Databse Connection is null!");
		}
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(query);
			
			if(ID > 0)
				st.setInt(1, ID);
			
			rs = st.executeQuery();
			
			return rs;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return null;
	}
}