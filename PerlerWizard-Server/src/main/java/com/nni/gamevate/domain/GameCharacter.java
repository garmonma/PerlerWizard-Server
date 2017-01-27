package com.nni.gamevate.domain;

public class GameCharacter {

	private int characterID;
	private int health;
	private int gold;
	private int experience;
	
	
	public GameCharacter(){
		
	}


	public int getCharacterID() {
		return characterID;
	}


	public int getHealth() {
		return health;
	}


	public int getGold() {
		return gold;
	}


	public int getExperience() {
		return experience;
	}


	public void setCharacterID(int characterID) {
		this.characterID = characterID;
	}


	public void setHealth(int health) {
		this.health = health;
	}


	public void setGold(int gold) {
		this.gold = gold;
	}


	public void setExperience(int experience) {
		this.experience = experience;
	}


	@Override
	public String toString() {
		return String.format("GameCharacter [characterID=%s, health=%s, gold=%s, experience=%s]", characterID, health,
				gold, experience);
	}
	
	
	
	
}
