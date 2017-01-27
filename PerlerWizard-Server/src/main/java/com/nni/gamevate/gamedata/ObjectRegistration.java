package com.nni.gamevate.gamedata;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.nni.gamevate.domain.GameCharacter;

public class ObjectRegistration {

	private ObjectRegistration(){}
	
	public static void register(Server server){
		Kryo kyro = server.getKryo();
		kyro.register(GameCharacter.class);
	}
}
