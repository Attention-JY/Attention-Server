package com.hifly.attention.serverCore;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;

import com.hifly.attention.client.User;
import com.hifly.attention.debuger.Debuger;

import com.hifly.attention.values.UnChangableValues;

public class FileServer extends Thread{
	
	private ServerSocket serverSocket;

	public FileServer() {
		init();
		this.start();
	}
	
	public void init() {
		try {
			serverSocket = new ServerSocket(UnChangableValues.FILE_SERVER_PORT);
		} catch (IOException e) {
			Debuger.printError(e);
		}
	}
	
	@Override
	public void run() {
		while(true) {
			Socket socket;
			try {
				Debuger.log(this.toString(), "\n\nFile Listen");
				socket = serverSocket.accept();
				String ip = socket.getInetAddress().getHostAddress();
				Debuger.log(this.toString(), "Accept! fileSocket" + ip + "���� �����Ͽ����ϴ�.");
				
				/* 
				 * �´� IP�� ã���� �ű⿡ ���� ������ set
				 * stream�� ������
				 *  */
				Iterator<String> it = MessageServer.users.keySet().iterator();				
				while(it.hasNext()){
					String val = it.next();
					User user = MessageServer.users.get(val);
					if(ip.equals(user.getIp())){
						MessageServer.users.get(val).setFileSocket(socket);
						MessageServer.users.get(val).setFileStream(socket);
					}
				}
				
			} catch (IOException e) {
				Debuger.printError(e);
			}
		}
	}
}