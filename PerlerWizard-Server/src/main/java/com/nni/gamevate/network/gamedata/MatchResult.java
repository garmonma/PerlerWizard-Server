package com.nni.gamevate.network.gamedata;

public class MatchResult {
	
	public int character_id;
	public int gold;
	public int experience;
	public int health;
	public boolean storyMode;
	public int node;
	
	@Override
	public String toString() {
		return String.format("MatchResult [character_id=%s, gold=%s, experience=%s, health=%s, storyMode=%s, node=%s]",
				character_id, gold, experience, health, storyMode, node);
	}
	
	
	
	

}
