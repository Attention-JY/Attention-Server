package com.hifly.attention.serverCore;

import java.net.Socket;
import java.util.HashMap;

import com.hifly.attention.client.User;
import com.hifly.attention.debuger.Debuger;
import com.hifly.attention.perform.BroadcastingPerform;
import com.hifly.attention.perform.CallingPerform;
import com.hifly.attention.perform.Chatting_MessagePerform;
import com.hifly.attention.perform.ComeAgainPerform;

import com.hifly.attention.perform.RoomOutPerform;
import com.hifly.attention.perform.UserEnrollPerform;
import com.hifly.attention.perform.UserFriendsRequestPerform;
import com.hifly.attention.values.Protocol;

public class ServiceThread extends Thread {
	private User user;
	private HashMap<String, SignalPerform> signalPerformHashMap;
	
	public ServiceThread(Socket socket) {
		user = new User(socket);
		user.setIp(socket.getInetAddress().getHostAddress());
		Debuger.log(this.toString(), "Thread : " + Thread.activeCount());
		
		
		/* Init signalPerformHashMap */
		signalPerformHashMap = new HashMap<String, SignalPerform>();		
		
		//Put Perform Class
		signalPerformHashMap.put(Protocol.USER_ENROLL_PROTOCOL, new UserEnrollPerform(user));
		signalPerformHashMap.put(Protocol.USER_FRIENDS_REQUEST_PROTOCOL, new UserFriendsRequestPerform(user));
		signalPerformHashMap.put(Protocol.ROOM_OUT_PROTOCOL, new RoomOutPerform(user));
		signalPerformHashMap.put(Protocol.CALLING_PROTOCOL, new CallingPerform(user));
		signalPerformHashMap.put(Protocol.BROADCAST_PROTOCOL, new BroadcastingPerform(user));
		signalPerformHashMap.put(Protocol.COME_AGAIN_PROTOCOL, new ComeAgainPerform(user));	
		signalPerformHashMap.put(Protocol.CHATTING_MESSAGE_PROTOCOL, new Chatting_MessagePerform(user));				
		
	}
	
	public void run() {
		while (true) {
			String message = user.readUTF();
			Debuger.log(this.toString(), "Init message  :  " + message);
			if (message == null) {
				
				user.disConnection(); //�ܼ��� ���ϸ� ����
				Debuger.log(this.toString(), "  ���� ����!!");
				Debuger.log(this.toString(), "Thread : " + Thread.activeCount());
				Debuger.log(this.toString(), user.getIp() + "����!");
				return;
			}
			
			SignalKey signalKey = new SignalKey();
			String headerProtocol = message.split(Protocol.SPLIT_MESSAGE)[0];
			String bodyData = message.substring(headerProtocol.length() + Protocol.SPLIT_MESSAGE.length());
			signalKey.setHeaderProtocol(headerProtocol);
			signalKey.setBodyData(bodyData);
			Debuger.log(this.toString(), "Init protocol header  :  " + headerProtocol);
			Debuger.log(this.toString(), "Init body data  :  " + bodyData);
			
			SignalPerform performClass = signalPerformHashMap.get(headerProtocol);
			performClass.performAction(signalKey);
		}
	}
}