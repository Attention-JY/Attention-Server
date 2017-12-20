package com.hifly.attention.serverCore;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import com.hifly.attention.client.Room;
import com.hifly.attention.client.User;
import com.hifly.attention.dao.UserDAO;
import com.hifly.attention.debuger.Debuger;
import com.hifly.attention.values.UnChangableValues;

public class MessageServer {
	
	private ServerSocket serverSocket;
	
	/*
	HashMap���� �����ϱ� ������ ���� remove ������ �ʾƵ� ���� uuid�� ������ �״�� �Ȱ��� key�� �����Ͱ� ������ ������
	���� remove�� ������ �ʿ䰡 ����!   ������...
	*/
	public static HashMap<String, User> users;
	public static HashMap<String, Room> rooms;
	
	public MessageServer() {
		init();
		acceptClient();
	}
	
	public void init() {
		try {
			serverSocket = new ServerSocket(UnChangableValues.MESSAGE_SERVER_PORT);
			users = new HashMap<String, User>();
			rooms = new HashMap<String, Room>();
			
			/* Init HashUser */
			users = UserDAO.getInstance().getUsers();
			
		} catch (IOException e) {
			Debuger.printError(e);
		}
	}
	
	public void acceptClient() {
		while(true) {
			Socket messageSocket;
			try {
				Debuger.log(this.toString(), "\n\nMessage Listen");
				messageSocket = serverSocket.accept();
				Debuger.log(this.toString(), "Accept! messageSocket" + messageSocket.getInetAddress().getHostAddress() + "���� �����Ͽ����ϴ�.");
				ServiceThread serviceThread = new ServiceThread(messageSocket);
				serviceThread.start();
			} catch (IOException e) {
				Debuger.printError(e);
			}
		}
	}
}