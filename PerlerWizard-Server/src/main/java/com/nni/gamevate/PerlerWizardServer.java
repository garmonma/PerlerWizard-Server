package com.nni.gamevate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.nni.gamevate.domain.GameCharacter;
import com.nni.gamevate.gamedata.DatabaseConnection;
import com.nni.gamevate.gamedata.ObjectRegistration;
import com.nni.gamevate.gamedata.dal.GameCharacterDAL;

public class PerlerWizardServer {

	private Server server;
	private DatabaseConnection dbConnection;
	
	private GameCharacterDAL gameCharacterDAL;

	public PerlerWizardServer() {
		dbConnection = new DatabaseConnection();
		gameCharacterDAL = new GameCharacterDAL();
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
					System.out.println("Character Received : " + character.getCharacterID());
					
					GameCharacter responseCharacter = 
							gameCharacterDAL.getCharacter(dbConnection.getConnection(), character.getCharacterID());
					
					connection.sendTCP(responseCharacter);
					System.out.println("Character Sent : \n" + responseCharacter);
					
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
