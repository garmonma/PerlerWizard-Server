package com.nni.gamevate.network.gamedata.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.nni.gamevate.network.gamedata.GameCharacter;
import com.nni.gamevate.network.gamedata.MatchResult;
import com.nni.gamevate.network.gamedata.dal.GameCharacterDAL;

public class MatchService {
	
	
	
	private GameCharacterDAL gameCharacterDAL;
	
	public MatchService(){
		gameCharacterDAL = new GameCharacterDAL();
	}
	
	
	
	public boolean cumulate(MatchResult matchResult, Connection dbConn) throws SQLException{
		GameCharacter gc = 
				gameCharacterDAL.getCharacter(dbConn, matchResult.character_id);
		
		int experience = gc.getExperience() + matchResult.experience;
		int gold = gc.getGold() + matchResult.gold;
		int health = matchResult.health;
		
		gc.setExperience(experience);
		gc.setGold(gold);
		gc.setHealth(health);
		
		return gameCharacterDAL.updateCharacter(dbConn, gc);
	}

}
