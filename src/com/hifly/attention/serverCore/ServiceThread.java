package com.hifly.attention.serverCore;

import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import com.hifly.attention.client.User;
import com.hifly.attention.debuger.Debuger;
import com.hifly.attention.perform.BroadcastingPerform;
import com.hifly.attention.perform.CallingPerform;
import com.hifly.attention.perform.RoomInFirstPerform;
import com.hifly.attention.perform.RoomMessagePerform;
import com.hifly.attention.perform.RoomOutPerform;
import com.hifly.attention.perform.UserEnrollPerform;
import com.hifly.attention.perform.UserFriendsRequestPerform;
import com.hifly.attention.values.Protocol;

public class ServiceThread extends Thread {
	private User user;
	private HashMap<String, SignalPerform> signalPerformHashMap;
	
	public ServiceThread(Socket socket) {
		user = new User(socket);
		Debuger.log(this.toString(), "Thread : " + Thread.activeCount());
		
		signalPerformHashMap = new HashMap<String, SignalPerform>();
		
		signalPerformHashMap.put(Protocol.USER_ENROLL_PROTOCOL, new UserEnrollPerform(user));
		signalPerformHashMap.put(Protocol.USER_FRIENDS_REQUEST_PROTOCOL, new UserFriendsRequestPerform(user));
		signalPerformHashMap.put(Protocol.ROOM_MESSAGE_PROTOCOL, new RoomMessagePerform(user));
		signalPerformHashMap.put(Protocol.ROOM_IN_FIRST_PROTOCOL, new RoomInFirstPerform(user));
		signalPerformHashMap.put(Protocol.ROOM_OUT_PROTOCOL, new RoomOutPerform(user));
		signalPerformHashMap.put(Protocol.CALLING, new CallingPerform(user));
		signalPerformHashMap.put(Protocol.BROADCAST, new BroadcastingPerform(user));
	}
	
	public void run() {
		while (true) {
			String message = user.readUTF();
			Debuger.log(this.toString(), "Init message  :  " + message);
			if (message == null) {
				//Server.users.remove(user);
				user.disConnection(); //�ܼ��� ���ϸ� ����
				Debuger.log(this.toString(), "  ���� ����!!");
				Debuger.log(this.toString(), "Thread : " + Thread.activeCount());
				Debuger.log(this.toString(), user.getIp() + "����!");
				return;
			}
			

			SignalKey signalKey = new SignalKey();
			String protocolHeader = message.split(Protocol.SPLIT_MESSAGE)[0];
			signalKey.setProtocol(protocolHeader);
			Debuger.log(this.toString(), "Init protocol header  :  " + protocolHeader);
			
			SignalPerform performClass = signalPerformHashMap.get(signalKey.getProtocol());
			performClass.performAction(signalKey);			
		}
	}
}