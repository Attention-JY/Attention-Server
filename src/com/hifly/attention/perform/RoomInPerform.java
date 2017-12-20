package com.hifly.attention.perform;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.hifly.attention.client.Room;
import com.hifly.attention.client.User;
import com.hifly.attention.dao.RoomDAO;
import com.hifly.attention.dao.RoomUsersDAO;
import com.hifly.attention.debuger.Debuger;
import com.hifly.attention.serverCore.MessageServer;
import com.hifly.attention.serverCore.SignalKey;
import com.hifly.attention.serverCore.SignalPerform;
import com.hifly.attention.values.Protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomInPerform implements SignalPerform {

	private User user;

	public RoomInPerform(User user) {
		this.user = user;
	}

	@Override
	public void performAction(SignalKey signalKey) {
		String bodyData = signalKey.getBodyData();
		Debuger.log(this.toString(), bodyData);

		String split[] = bodyData.split(Protocol.SPLIT_MESSAGE);

		String roomUuid = split[0];
		int len = Integer.parseInt(split[1]);


		Date date = new Date();
		long now = System.currentTimeMillis();
		String currentTime;
		SimpleDateFormat simpleDataFormat = new SimpleDateFormat("HH:mm", Locale.KOREA);
		date.setTime(now);
		currentTime = simpleDataFormat.format(date);
		
		Room room = new Room(roomUuid);
		RoomDAO.getInstance().insertRoom(roomUuid, currentTime, "Server", "ä�ù濡 �ʴ�Ǽ̽��ϴ�.", "Server");
		for (int i = 1; i <= len; i++) {
			room.addUser(split[1 + i]);
			RoomUsersDAO.getInstance().insertRoomUsers(roomUuid, split[1 + i]); //�ȿ� �ִ� ����� 1
		}
		
		MessageServer.rooms.put(roomUuid, room); // �� �����
		
		room.broadcast(Protocol.CHATTING_MESSAGE_PROTOCOL + Protocol.SPLIT_MESSAGE + "Server" + Protocol.SPLIT_MESSAGE
				+ "ä�ù濡 �ʴ�Ǽ̽��ϴ�." + Protocol.SPLIT_MESSAGE + currentTime + Protocol.SPLIT_MESSAGE + "Server"
				+ Protocol.SPLIT_MESSAGE + roomUuid + Protocol.SPLIT_MESSAGE);
	}
}