package com.nni.gamevate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.nni.gamevate.network.ObjectRegistration;
import com.nni.gamevate.network.gamedata.GameCharacter;
import com.nni.gamevate.network.gamedata.MatchResult;
import com.nni.gamevate.network.gamedata.dal.DatabaseConnection;
import com.nni.gamevate.network.gamedata.dal.GameCharacterDAL;
import com.nni.gamevate.network.gamedata.services.MatchService;

public class PerlerWizardServer {

	private Server server;
	private DatabaseConnection dbConnection;
	private GameCharacterDAL gameCharacterDAL;
	private MatchService matchService;

	public PerlerWizardServer() {
		dbConnection = new DatabaseConnection();
		gameCharacterDAL = new GameCharacterDAL();
		matchService = new MatchService();
	}

	public void start() {
		server = new Server(){
			@Override
			protected Connection newConnection() {
				return super.newConnection();
			}
		};
		
		ObjectRegistration.register(server);

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if( object instanceof GameCharacter){
					GameCharacter character = (GameCharacter) object;
					System.out.println("Character Received : " + character);
					
					GameCharacter responseCharacter = 
							gameCharacterDAL.getCharacter(dbConnection.getConnection(), character.getCharacterID());
					
					connection.sendTCP(responseCharacter);
					System.out.println("Character Sent : \n" + responseCharacter);
					
				}
				
				if(object instanceof MatchResult){
					MatchResult matchResult = (MatchResult) object;
					System.out.println("Match Result Recieved : " + matchResult );
					boolean updated = false;
					
					try {
						 updated = matchService.cumulate(matchResult, dbConnection.getConnection());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					if(updated){
						connection.sendTCP(gameCharacterDAL.getCharacter(dbConnection.getConnection(), matchResult.character_id));
						System.out.println("Updated Character Sent");
					}
				}	
			}
		});
		
		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		server.start();
		dbConnection.start();
	}
	
	public void stop(){
		server.stop();
		dbConnection.close();
	}
	
	public String getServerStatus(){
		return server.toString();
	}
	
	public String[] getConnectedClients(){
		Connection[] connections = server.getConnections();
		String[] connectionDataList = new String[10];
		for(int i = 0; i < connections.length; i++){
			connectionDataList[i] = connections[i].toString();
		}
		
		return connectionDataList;
	}
	
	public String getCharacters(){
		return gameCharacterDAL.getCharacters(dbConnection.getConnection()).toString();
	}

}
