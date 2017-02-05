package com.nni.gamevate.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.nni.gamevate.network.gamedata.GameCharacter;
import com.nni.gamevate.network.gamedata.MatchResult;

public class ObjectRegistration {

	private ObjectRegistration(){}
	
	public static void register(Server server){
		Kryo kyro = server.getKryo();
		kyro.register(GameCharacter.class);
		kyro.register(MatchResult.class);
	}
}
