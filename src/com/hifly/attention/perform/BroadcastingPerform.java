package com.hifly.attention.perform;

import java.util.Iterator;

import com.hifly.attention.client.User;
import com.hifly.attention.debuger.Debuger;
import com.hifly.attention.serverCore.MessageServer;
import com.hifly.attention.serverCore.SignalKey;
import com.hifly.attention.serverCore.SignalPerform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BroadcastingPerform implements SignalPerform {
	
	private User user;
	
	public BroadcastingPerform(User user) {
		this.user = user;
	}
	
	@Override
	public void performAction(SignalKey signalKey) {
		String bodyData = signalKey.getBodyData();
		Debuger.log(this.toString(), bodyData);
		broadcast(bodyData);
	}
	
	private void broadcast(String message){
		Iterator<String> it = MessageServer.users.keySet().iterator();
		
		while(it.hasNext()){
			User user = MessageServer.users.get(it.next());
			user.sendUTF(message);
		}
	}
}
