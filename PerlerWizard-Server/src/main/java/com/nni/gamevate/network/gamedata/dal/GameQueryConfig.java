package com.nni.gamevate.network.gamedata.dal;

public class GameQueryConfig {

	private GameQueryConfig(){}	
	
	public static final String GET_PLAYERS = "SELECT * FROM players";
	public static final String GET_CHARACTERS = "SELECT * FROM \"GameData\".characters";
	public static final String GET_CHARACTER = "SELECT * FROM \"GameData\".characters  WHERE character_id = ?";
	public static final String SET_GOLD = "INSERT INTO ";
	
	public static final String UPDATE_CHARACTER = "UPDATE \"GameData\".characters "
			+ "SET experience = ?, gold = ?, health = ? "
			+ "WHERE character_id = ?";
;}
